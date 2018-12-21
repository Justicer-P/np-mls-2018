package com.sf.marathon.np.index.api;

import com.sf.marathon.np.index.api.domain.LogData;
import com.sf.marathon.np.index.client.IndexClient;
import com.sf.marathon.np.index.domain.EsConfig;
import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.common.joda.time.DateTime;
import org.elasticsearch.common.joda.time.format.DateTimeFormat;
import org.elasticsearch.common.joda.time.format.DateTimeFormatter;

import java.util.Map;

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
public class API {
    static {
        EsConfig instance = EsConfig.getInstance();
        instance.setEsUrl("10.202.39.151:9300");
        instance.setClusterName("elasticsearch");
    }

    public void save(LogData logData){
        IndexClient indexClient = new IndexClient();
        Map<String, Object> dataMap = Maps.newHashMap();
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        String sss = logData.getReqTime();
        dataMap.put("reqtime", DateTime.parse(sss, format).toDate().getTime());
        dataMap.put("sourceip", logData.getSourceIp());
        dataMap.put("destip", logData.getDestIp());
        dataMap.put("url", logData.getUrl());
        dataMap.put("maxresponsetime", logData.getMaxReponseTime());
        indexClient.save("log_" + logData.getReqTime().substring(0, 10), logData.getType().toString(), dataMap);
    }


}
