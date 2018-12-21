package com.sf.marathon.np.index.client;

import com.sf.marathon.np.index.api.domain.LogData;
import com.sf.marathon.np.index.clause.*;
import com.sf.marathon.np.index.domain.FieldType;
import com.sf.marathon.np.index.domain.IndexFieldType;
import com.sf.marathon.np.index.domain.KeyBean;
import com.sf.marathon.np.index.domain.RowBean;
import com.sf.marathon.np.index.exception.ESClientException;
import com.sf.marathon.np.index.util.ExceptionUtils;
import com.sf.marathon.np.index.util.StringUtil;
import com.sf.marathon.np.index.util.Tuple;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.common.joda.time.DateTime;
import org.elasticsearch.common.joda.time.format.DateTimeFormat;
import org.elasticsearch.common.joda.time.format.DateTimeFormatter;
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
    public static final String MARATHON = "6marathon6";

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
        } catch (Exception e) {
            if (ExceptionUtils.isException(e, IndexMissingException.class)) {
                IndexAdminClient indexAdminClient = new IndexAdminClient();
                indexAdminClient.createDb(indices[0]);
                indexAdminClient.createTable(indices[0], type, Lists.newArrayList(FieldType.type("reqTime", IndexFieldType.LONG),
                        FieldType.type("sourceip"), FieldType.type("destip"), FieldType.type("requestTimes", IndexFieldType.INT)
                        , FieldType.type("url"), FieldType.type("errorTimes", IndexFieldType.INT)
                        , FieldType.type("minReponseTime", IndexFieldType.DOUBLE),
                        FieldType.type("minReponseTime", IndexFieldType.DOUBLE),
                        FieldType.type("ninePercentResponseTime", IndexFieldType.DOUBLE)
                ), true);
                return mulTiAggregation(type, searchClause, groupBy, indices);
            } else {
                throw e;
            }

        }
    }

    @Override
    public void batchSave(List<LogData> logDatas) {
        BulkRequestBuilder bulkRequest = ElasticClient.instance.getClient().prepareBulk();
        for (LogData logData : logDatas) {

            Map<String, Object> dataMap = toDataMap(logData);
            bulkRequest.add(ElasticClient.instance.getClient().prepareIndex("log_" + logData.getReqTime().substring(0, 10), logData.getType().toString())
                    .setSource(dataMap
                    ));
            if (bulkRequest.numberOfActions() == 1000) {
                BulkResponse bulkItemResponses = bulkRequest.execute().actionGet();
                dealOnError(logDatas, bulkItemResponses);
            }
        }
        if (bulkRequest.numberOfActions() > 0) {
            BulkResponse bulkItemResponses = bulkRequest.execute().actionGet();
            dealOnError(logDatas, bulkItemResponses);

        }
    }

    private void dealOnError(List<LogData> logDatas, BulkResponse bulkItemResponses) {
        if (bulkItemResponses.hasFailures()) {
            String s = bulkItemResponses.buildFailureMessage();
            if (!StringUtil.isEmpty(s) && s.contains("IndexMissingException")) {
                IndexAdminClient indexAdminClient = new IndexAdminClient();
                String index = "log_" + logDatas.get(0).getReqTime().substring(0, 10);
                indexAdminClient.createDb(index);
                indexAdminClient.createTable(index, logDatas.get(0).getType().toString(), Lists.newArrayList(FieldType.type("reqTime", IndexFieldType.DATE),
                        FieldType.type("sourceip"), FieldType.type("destip"), FieldType.type("requestTimes", IndexFieldType.INT)
                        , FieldType.type("requrl"), FieldType.type("errorTimes", IndexFieldType.INT)
                        , FieldType.type("maxResponseTime", IndexFieldType.DOUBLE),
                        FieldType.type("minResponseTime", IndexFieldType.DOUBLE),
                        FieldType.type("avgResponseTime", IndexFieldType.DOUBLE),
                        FieldType.type("ninePercentResponseTime", IndexFieldType.DOUBLE)
                ), true);

                batchSave(logDatas);
            }
        }
    }

    private Map<String, Object> toDataMap(LogData logData) {
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
        return dataMap;
    }

    @Override
    public void save(String index, String type, Map<String, Object> dataMap) {
        try {
            ElasticClient.instance.getClient().prepareIndex(index, type).setSource(dataMap).execute().actionGet();
        } catch (Exception e) {
            if (ExceptionUtils.isException(e, IndexMissingException.class)) {
                IndexAdminClient indexAdminClient = new IndexAdminClient();
                indexAdminClient.createDb(index);
                indexAdminClient.createTable(index, type, Lists.newArrayList(FieldType.type("reqTime", IndexFieldType.DATE),
                        FieldType.type("sourceip"), FieldType.type("destip"), FieldType.type("requestTimes", IndexFieldType.INT)
                        , FieldType.type("requrl"), FieldType.type("errorTimes", IndexFieldType.INT)
                        , FieldType.type("maxResponseTime", IndexFieldType.DOUBLE),
                        FieldType.type("minResponseTime", IndexFieldType.DOUBLE),
                        FieldType.type("avgResponseTime", IndexFieldType.DOUBLE),
                        FieldType.type("ninePercentResponseTime", IndexFieldType.DOUBLE)
                ), true);
                save(index, type, dataMap);
            } else {
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
//                key = key.replaceAll(MARATHON, "/");
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
