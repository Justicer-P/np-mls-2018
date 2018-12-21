package com.sf.marathon.np.index.api;

import com.sf.marathon.np.index.api.domain.LogData;
import com.sf.marathon.np.index.clause.AggregationClause;
import com.sf.marathon.np.index.clause.GroupBy;
import com.sf.marathon.np.index.clause.SearchClause;
import com.sf.marathon.np.index.client.IndexClient;
import com.sf.marathon.np.index.domain.EsConfig;
import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.common.joda.time.DateTime;
import org.elasticsearch.common.joda.time.format.DateTimeFormat;
import org.elasticsearch.common.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.sf.marathon.np.index.clause.AggregationClause.AggregationType.AVG;
import static com.sf.marathon.np.index.clause.AggregationClause.AggregationType.MAX;
import static com.sf.marathon.np.index.clause.AggregationClause.AggregationType.MIN;

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
