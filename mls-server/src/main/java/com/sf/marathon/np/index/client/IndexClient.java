package com.sf.marathon.np.index.client;

import com.sf.marathon.index.clause.*;
import com.sf.marathon.index.domain.FieldType;
import com.sf.marathon.index.domain.IndexFieldType;
import com.sf.marathon.index.domain.KeyBean;
import com.sf.marathon.index.domain.RowBean;
import com.sf.marathon.index.exception.ESClientException;
import com.sf.marathon.index.util.ExceptionUtils;
import com.sf.marathon.index.util.StringUtil;
import com.sf.marathon.index.util.Tuple;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.indices.IndexMissingException;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.NumericMetricsAggregation;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;
import java.util.Map;

/**
 * 描述:
 * <p>
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE            PERSON          REASON
 *  1    2018/12/20     204062          Create
 * ****************************************************************************
 * </pre>
 *
 * @author 204062
 * @since 1.0
 */
public class IndexClient implements IIndexClient {
    private static final String GROUP_KEY_SPLITTER = "~";
    public static final int DEFAULT_PARTITION = 5;
    public static final int PAGING_THRESHOLD = 50000;

    @Override
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

    @Override
    public Map<String, Number[]> mulTiAggregation(String type, SearchClause searchClause, GroupBy groupBy, String... indices) {
        try {
            SearchRequestBuilder searchRequestBuilder = initCountBuilder(searchClause, type, indices);
            AggregationClause[] aggregationClauses = groupBy.getAggregationClauses();
            for (AggregationClause aggregationClause : aggregationClauses) {
                searchRequestBuilder.addAggregation(groupBy.build(aggregationClause));
            }

            SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
            return getDataMapWithMultiFieldResult(groupBy, aggregationClauses, searchResponse);
        }catch (Exception e){
            if(ExceptionUtils.isException(e, IndexMissingException.class)){
                IndexAdminClient indexAdminClient = new IndexAdminClient();
                indexAdminClient.createDb(indices[0]);
                indexAdminClient.createTable(indices[0],type, Lists.newArrayList(FieldType.type("writeDate", IndexFieldType.LONG),
                FieldType.type("sourceip"),FieldType.type("destip"),FieldType.type("status"),FieldType.type("url"),FieldType.type("responsetime",IndexFieldType.DOUBLE)),true);
                return mulTiAggregation(type,searchClause,groupBy,indices);
            }else {
                throw e;
            }

        }
    }

    @Override
    public void save(String index, String type, Map<String, Object> dataMap) {
        try {
            ElasticClient.instance.getClient().prepareIndex(index, type).setSource(dataMap).execute().actionGet();
        }catch (Exception e){
            if(ExceptionUtils.isException(e, IndexMissingException.class)){
                IndexAdminClient indexAdminClient = new IndexAdminClient();
                indexAdminClient.createDb(index);
                indexAdminClient.createTable(index,type, Lists.newArrayList(FieldType.type("writeDate", IndexFieldType.LONG),
                        FieldType.type("sourceip"),FieldType.type("destip"),FieldType.type("status"),FieldType.type("url"),FieldType.type("responsetime",IndexFieldType.DOUBLE)),true);
                save(index,type,dataMap);
            }else {
                throw e;
            }
        }
    }

    protected SearchRequestBuilder initCountBuilder(SearchClause searchClause, String type, String[] indices) {
        SearchRequestBuilder searchRequestBuilder = ElasticClient.instance.getClient().prepareSearch(indices);
        // 设置查询索引类型
        searchRequestBuilder.setTypes(type);
        searchRequestBuilder.setSearchType(SearchType.COUNT);
        if (searchClause != null) {
            searchRequestBuilder.setQuery(searchClause.build());
        }
        return searchRequestBuilder;
    }

    private Map<String, Number[]> getDataMapWithMultiFieldResult(GroupBy groupBy, AggregationClause[] aggregationClauses, SearchResponse searchResponse) {
        Map<String, Number[]> dataMaps = Maps.newHashMap();
        int length = aggregationClauses.length;

        for (int i = 0; i < length; i++) {
            String itemName = aggregationClauses[i].getFieldName();
            Terms terms = searchResponse.getAggregations().get(itemName);
            StringBuffer keyBuffer = new StringBuffer();
            AggregationRecurser aggregationRecurser = getAggregationRecurser(groupBy, dataMaps, length, i, itemName, terms, keyBuffer);
            int startLoop = 1;
            recurse(aggregationRecurser, aggregationRecurser.getAggregation(), startLoop);
        }
        return dataMaps;
    }

    private AggregationRecurser getAggregationRecurser(GroupBy groupBy, Map<String, Number[]> dataMaps, int length, int i, String itemName, Terms terms, StringBuffer keyBuffer) {
        AggregationRecurser aggregationRecurser = new AggregationRecurser(keyBuffer, terms, groupBy.getGroupFields(), itemName);
        aggregationRecurser.setDataIndex(i);
        aggregationRecurser.setGroupMaps(dataMaps);
        aggregationRecurser.setLength(length);
        return aggregationRecurser;
    }

    private void recurse(AggregationRecurser aggregationRecurser, Terms aggregation, int startIndex) {
        if (startIndex == aggregationRecurser.getGroupFields().length) {
            List<Terms.Bucket> termBucket = aggregation.getBuckets();
            for (Terms.Bucket bucket : termBucket) {
                NumericMetricsAggregation.SingleValue singleValue = bucket.getAggregations().get(aggregationRecurser.getFieldName());
                StringBuffer keyBuffer = aggregationRecurser.getKeyBuffer();
                String key = keyBuffer.toString() + GROUP_KEY_SPLITTER + bucket.getKey();
                if (keyBuffer.toString().isEmpty()) {
                    key = bucket.getKey();
                }
                if (aggregationRecurser.getGroupMaps() != null) {
                    Map<String, Number[]> maps = aggregationRecurser.getGroupMaps();
                    Number[] result = maps.get(key);
                    if (result == null) result = new Number[aggregationRecurser.getLength()];
                    result[aggregationRecurser.getDataIndex()] = singleValue.value();
                    maps.put(key, result);
                } else {
                    aggregationRecurser.getMaps().put(key, singleValue.value());
                }
            }
            reset(aggregationRecurser.getKeyBuffer());
        } else {
            for (Terms.Bucket bucket : aggregation.getBuckets()) {
                Terms terms = bucket.getAggregations().get(aggregationRecurser.getGroupField(startIndex));
                keyBuffer(aggregationRecurser.getKeyBuffer(), bucket);
                recurse(aggregationRecurser, terms, startIndex + 1);
            }

        }
    }

    private void reset(StringBuffer keyBuffer) {
        keyBuffer.delete(0, keyBuffer.length());
    }

    private void keyBuffer(StringBuffer keyBuffer, Terms.Bucket bucket) {
        if (keyBuffer.length() == 0) {
            keyBuffer.append(bucket.getKey());
        } else {
            keyBuffer.append("-").append(bucket.getKey());
        }
    }
}
