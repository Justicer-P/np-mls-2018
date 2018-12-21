package com.sf.marathon.np.index.api;

import com.sf.marathon.np.index.api.domain.LogData;
import org.elasticsearch.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.*;

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
@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
//@ContextConfiguration(locations = {"classpath:META-INF/spring/beans-mybatis-config.xml"})
public class APITest {
    @Autowired
    private API api;
    @Test
    public void save() {
//        Map<String, Object> dataMap = Maps.newHashMap();
//        dataMap.put("writeDate", System.currentTimeMillis());
//        dataMap.put("sourceip", "10.116.88.11");
//        dataMap.put("destip", "10.116.178.26");
//        dataMap.put("status", "200");
//        dataMap.put("url", "/express/pickupservice/confirm/getTaskDetail?t=1");
//        dataMap.put("responsetime", 0.013);
        LogData logData = new LogData();
        logData.setReqTime("2018-12-21 14:14");
        logData.setUrl("/express/pickupservice/confirm/getTaskDetail?t=1");
        logData.setMaxReponseTime(3.51d);
        logData.setMinReponseTime(2.16d);
        logData.setType(GroupType.URL_TYPE);
        new API().save(logData);
    }

    @Test
    public void mulTiAggregation() {
        Map<String, Number[]> stringMap = api.mulTiAggregation("2018-12-21", GroupType.URL_TYPE);
        System.out.println("stringMap = " + stringMap);
    }
}