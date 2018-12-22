package com.sf.marathon.np.index.api;

import com.sf.marathon.np.index.api.domain.LogData;
import com.sf.marathon.np.index.clause.*;
import com.sf.marathon.np.index.client.ElasticClient;
import com.sf.marathon.np.index.client.IndexClient;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.sf.marathon.np.index.clause.AggregationClause.AggregationType.*;
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
    @Autowired
    private IndexClient indexClient;

    public void save(LogData logData) {
        Map<String, Object> dataMap = Maps.newHashMap();
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        String sss = logData.getReqTime();
        dataMap.put("reqTime", DateTime.parse(sss, format).toDate().getTime());
        dataMap.put("sourceip", logData.getSourceIp());
        dataMap.put("destip", logData.getDestIp());
        dataMap.put("requrl", logData.getUrl());
        dataMap.put("requestTimes", logData.getRequestTimes());
        dataMap.put("errorTimes", logData.getErrorTimes());
        dataMap.put("maxResponseTime", logData.getMaxReponseTime());
        dataMap.put("minResponseTime", logData.getMinReponseTime());
        dataMap.put("avgResponseTime", logData.getAvgReponseTime());
        dataMap.put("ninePercentResponseTime", logData.getNinePercentResponseTime());
        dataMap.put("__index_tm__", new Date());
        indexClient.save(IndexClient.getDBName("log_" + logData.getReqTime().substring(0, 10), logData.getType().toString()), logData.getType().toString(), dataMap);
    }


    public void batchSave(List<LogData> logDatas) {
        indexClient.batchSave(logDatas);
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
        if ((pageIndex + 1) * pageSize > PAGING_THRESHOLD) {
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

    public Map<String, Number[]> mulTiAggregation(String startTime, String endTime, String url) {
        GroupBy groupBy = new GroupBy(new AggregationClause[]{new AggregationClause(MAX, "maxResponseTime"), new AggregationClause(MIN, "minResponseTime"),
                new AggregationClause(AVG, "avgResponseTime"),
                new AggregationClause(AVG, "ninePercentResponseTime")}
                , "reqTime", "requrl");
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        long start = DateTime.parse(startTime, format).toDate().getTime();
        long end = DateTime.parse(endTime, format).toDate().getTime();
        SearchClause searchClause = SearchClause.newClause().greaterOrEqual("reqTime", start)
                .and().lessOrEqual("reqTime", end);
        if (!StringUtil.isEmpty(url)) {
            searchClause.and().equal("requrl", url);
        }
        return indexClient.mulTiAggregation(GroupType.URL_TYPE.toString(), searchClause, groupBy, IndexClient.getDBName("log_" + startTime.substring(0, 10), GroupType.URL_TYPE.toString()));
    }

    public Map<String, Number[]> mulTiAggregation(String startTime, String endTime) {
        return mulTiAggregation(startTime, endTime, null);
    }

    public Map<String, Number[]> sumRequestGroupByURL(String startTime, String endTime, String url) {
        GroupBy groupBy = new GroupBy(new AggregationClause[]{new AggregationClause(SUM, "requestTimes"), new AggregationClause(SUM, "errorTimes")}
                , "reqTime", "requrl");
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        long start = DateTime.parse(startTime, format).toDate().getTime();
        long end = DateTime.parse(endTime, format).toDate().getTime();
        SearchClause searchClause = SearchClause.newClause().greaterOrEqual("reqTime", start)
                .and().lessOrEqual("reqTime", end);
        if (!StringUtil.isEmpty(url)) {
            searchClause.and().equal("requrl", url);
        }
        return indexClient.mulTiAggregation(GroupType.URL_TYPE.toString(), searchClause, groupBy, IndexClient.getDBName("log_" + startTime.substring(0, 10), GroupType.URL_TYPE.toString()));
    }

    /**
     * 请求成功失败次数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public Map<String, Number[]> sumRequestGroupByURL(String startTime, String endTime) {
        return sumRequestGroupByURL(startTime, endTime, null);
    }

    public Map<String, Number[]> groupBySourceIP(String startTime, String endTime) {
        return groupBySourceIP(startTime, endTime, null);
    }

    public Map<String, Number[]> groupBySourceIP(String startTime, String endTime, String sourceIp) {
        GroupBy groupBy = new GroupBy(new AggregationClause[]{new AggregationClause(SUM, "requestTimes"), new AggregationClause(SUM, "errorTimes")}
                , "reqTime", "sourceip");
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        long start = DateTime.parse(startTime, format).toDate().getTime();
        long end = DateTime.parse(endTime, format).toDate().getTime();
        SearchClause searchClause = SearchClause.newClause().greaterOrEqual("reqTime", start)
                .and().lessOrEqual("reqTime", end);
        if (!StringUtil.isEmpty(sourceIp)) {
            searchClause.and().equal("sourceip", sourceIp);
        }
        return indexClient.mulTiAggregation(GroupType.SOURCEIP.toString(), SearchClause.newClause(), groupBy, IndexClient.getDBName("log_" + startTime.substring(0, 10), GroupType.SOURCEIP.toString()));
    }

    public Map<String, Number[]> groupByDestIP(String startTime, String endTime, String destIp) {
        GroupBy groupBy = new GroupBy(new AggregationClause[]{new AggregationClause(SUM, "requestTimes"), new AggregationClause(SUM, "errorTimes")}
                , "reqTime", "destip");
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        long start = DateTime.parse(startTime, format).toDate().getTime();
        long end = DateTime.parse(endTime, format).toDate().getTime();
        SearchClause searchClause = SearchClause.newClause().greaterOrEqual("reqTime", start)
                .and().lessOrEqual("reqTime", end);
        if (!StringUtil.isEmpty(destIp)) {
            searchClause.and().equal("destip", destIp);
        }
        return indexClient.mulTiAggregation(GroupType.DESTIP.toString(), searchClause, groupBy, IndexClient.getDBName("log_" + startTime.substring(0, 10), GroupType.DESTIP.toString()));
    }

    public Map<String, Number[]> groupByDestIP(String startTime, String endTime) {
        return groupByDestIP(startTime, endTime, null);
    }
}
