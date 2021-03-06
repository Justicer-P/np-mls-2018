package com.sf.marathon.np.index.api;

import com.sf.marathon.np.index.api.domain.LogData;
import com.sf.marathon.np.index.clause.*;
import com.sf.marathon.np.index.client.ElasticClient;
import com.sf.marathon.np.index.client.IndexClient;
import com.sf.marathon.np.index.domain.EsConfig;
import com.sf.marathon.np.index.domain.KeyBean;
import com.sf.marathon.np.index.domain.RowBean;
import com.sf.marathon.np.index.exception.ESClientException;
import com.sf.marathon.np.index.util.StringUtil;
import com.sf.marathon.np.index.util.Tuple;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.common.joda.time.DateTime;
import org.elasticsearch.common.joda.time.format.DateTimeFormat;
import org.elasticsearch.common.joda.time.format.DateTimeFormatter;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.sf.marathon.np.index.clause.AggregationClause.AggregationType.AVG;
import static com.sf.marathon.np.index.clause.AggregationClause.AggregationType.MAX;
import static com.sf.marathon.np.index.clause.AggregationClause.AggregationType.MIN;
import static com.sf.marathon.np.index.client.IndexClient.PAGING_THRESHOLD;

/**
 * 描述:
 * <p>
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE            PERSON          REASON
 *  1    2018/12/21     204062          Create
 * ****************************************************************************
 * </pre>
 *
 * @author 204062
 * @since 1.0
 */
@Component
public class API {
//    static {
//        EsConfig instance = EsConfig.getInstance();
//        instance.setEsUrl("10.203.97.8:9300");
//        instance.setClusterName("elasticsearch");
//    }

    public void save(LogData logData) {
        IndexClient indexClient = new IndexClient();
        Map<String, Object> dataMap = Maps.newHashMap();
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        String sss = logData.getReqTime();
        dataMap.put("reqTime", DateTime.parse(sss, format).toDate().getTime());
        dataMap.put("sourceip", logData.getSourceIp());
        dataMap.put("destip", logData.getDestIp());
        dataMap.put("url", logData.getUrl());
        dataMap.put("errorTimes", logData.getErrorTimes());
        dataMap.put("maxresponsetime", logData.getMaxReponseTime());
        dataMap.put("minReponseTime", logData.getMinReponseTime());
        dataMap.put("ninePercentResponseTime", logData.getNinePercentResponseTime());
        indexClient.save("log_" + logData.getReqTime().substring(0, 10), logData.getType().toString(), dataMap);
    }

    public List<RowBean> findPageData(String type, SearchClause searchClause, int pageSize, int pageIndex, String... indices) {
        return getPageRowBeans(type, searchClause, pageSize, pageIndex, indices)._1;
    }

    protected Tuple<List<RowBean>, Long> getPageRowBeans(String type, SearchClause searchClause, int pageSize, int pageIndex, String[] indices) {
        checkOverDepth(pageSize, pageIndex);
        SearchRequestBuilder searchRequestBuilder = initSearchBuilder(type, indices, searchClause);
        searchRequestBuilder.setFrom(pageSize * pageIndex);
        // 设置查询结果集的最大条数
        searchRequestBuilder.setSize(pageSize);

        return getRowBeans(searchRequestBuilder);
    }

    protected SearchRequestBuilder initSearchBuilder(String type, String[] indices, SearchClause searchClause) {
        return initSearchBuilder(indices, searchClause, new String[]{type});
    }

    private void checkOverDepth(int pageSize, int pageIndex) {
        if ((pageIndex+1) * pageSize > PAGING_THRESHOLD) {
            throw new ESClientException("Query depth is over " + PAGING_THRESHOLD + ",reject!");
        }
    }

    private Tuple<List<RowBean>, Long> getRowBeans(SearchRequestBuilder searchRequestBuilder) {
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        SearchHits hits = response.getHits();
        List<RowBean> datas = Lists.newArrayList();
        for (SearchHit searchHit : hits) {
            RowBean rowBean = new RowBean(new KeyBean(searchHit.getId(), searchHit.getVersion()), searchHit.sourceAsMap());
            datas.add(rowBean);
        }
        return new Tuple<>(datas, hits.getTotalHits());
    }

    private SearchRequestBuilder initSearchBuilder(String[] indices, SearchClause searchClause, String[] types) {
        SearchRequestBuilder searchRequestBuilder = ElasticClient.instance.getClient().prepareSearch(indices);
        // 设置查询索引类型
        searchRequestBuilder.setTypes(types);
        searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);
        searchRequestBuilder.setVersion(true);
        if (searchClause != null) {
            String preference = searchClause.getPreference();
            configPreference(searchRequestBuilder, preference);
            searchRequestBuilder.setQuery(searchClause.build());
            orderBy(searchClause, searchRequestBuilder);
        }

        return searchRequestBuilder;
    }

    private void configPreference(SearchRequestBuilder searchRequestBuilder, String preference) {
        if (!StringUtil.isEmpty(preference))
            searchRequestBuilder.setPreference(preference);
    }

    protected void orderBy(SearchClause searchClause, SearchRequestBuilder searchRequestBuilder) {
        List<OrderClause> orderConditions = searchClause.getOrderClauses();
        orderByCondition(searchRequestBuilder, orderConditions);
    }

    private void orderByCondition(SearchRequestBuilder searchRequestBuilder, List<OrderClause> orderConditions) {
        if (!orderConditions.isEmpty()) {
            for (OrderClause orderCondition : orderConditions) {
                SortOrder order = orderCondition.getOrder() == Order.ASC ? SortOrder.ASC :
                        SortOrder.DESC;
                GeoPoint geoPoint = orderCondition.getGeoPoint();
                if (geoPoint != null) {
                    GeoDistanceSortBuilder location = new GeoDistanceSortBuilder(orderCondition.getFieldName()).point(geoPoint.getLat(), geoPoint.getLon())
                            .order(order);
                    searchRequestBuilder.addSort(location);
                } else {
                    searchRequestBuilder.addSort(orderCondition.getFieldName(), order);
                }

            }
        }
    }


    public Map<String, Number[]> mulTiAggregation(String reqTime,String startTime,String endTime) {
        GroupBy groupBy = new GroupBy(new AggregationClause[]{new AggregationClause(MAX, "maxresponsetime"),new AggregationClause(MIN, "minReponseTime"),
                new AggregationClause(AVG, "ninePercentResponseTime")}
                , "reqTime", "url");
        IndexClient indexClient = new IndexClient();
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        long start = DateTime.parse(startTime, format).toDate().getTime();
        long end = DateTime.parse(endTime, format).toDate().getTime();
        return indexClient.mulTiAggregation(GroupType.URL_TYPE.toString(), SearchClause.newClause().greaterOrEqual("reqTime",start)
                .and().lessOrEqual("reqTime",end), groupBy, "log_" + reqTime.substring(0, 10));
    }

    public  Map<String, Number[]> groupByDestIP(String reqTime,String startTime,String endTime){

        return null;
    }
}
