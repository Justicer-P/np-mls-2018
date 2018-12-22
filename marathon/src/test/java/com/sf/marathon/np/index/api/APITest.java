package com.sf.marathon.np.index.api;

import com.sf.marathon.np.index.api.domain.LogData;
import com.sf.marathon.np.index.clause.SearchClause;
import com.sf.marathon.np.index.domain.RowBean;
import org.elasticsearch.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class APITest {

    @Autowired
    private API api;

    @Test
    public void saveURL() throws UnsupportedEncodingException {
        LogData logData = new LogData();
        logData.setReqTime("2018-11-30 14:14");
//        String utf8 = URLEncoder.encode("/express/pickupservice", "UTF8");
        logData.setUrl("/express/pickupservice");
        logData.setMaxReponseTime(3.51d);
        logData.setMinReponseTime(2.16d);
        logData.setAvgReponseTime(3.5d);
        logData.setNinePercentResponseTime(5.4d);
        logData.setType(GroupType.URL_TYPE);
        api.save(logData);
    }

    @Test
    public void batchSaveUrl() {
        ArrayList<LogData> objects = Lists.newArrayList();

        for (int i = 0; i < 3; i++) {
            LogData logData = new LogData();
            logData.setReqTime("2018-11-21 14:14");
//            logData.setUrl(IndexClient.MARATHON + "express" + i + IndexClient.MARATHON + "pickupservice" + IndexClient.MARATHON + "getTaskDetail");
            logData.setUrl("/express/pickservice");
            logData.setMaxReponseTime(4.51d);
            logData.setMinReponseTime(3.16d);
            logData.setAvgReponseTime(2.5d);
            logData.setNinePercentResponseTime(6.4d);
            logData.setType(GroupType.URL_TYPE);
            objects.add(logData);
        }

        api.batchSave(objects);
    }

    @Test
    public void mulTiAggregationByUrl() {

        Map<String, Number[]> stringMap = api.mulTiAggregation("2018-11-21 00:10", "2018-11-21 15:30", "/express/pickservice");
        System.out.println("stringMap = " + stringMap);
        for (Map.Entry<String, Number[]> entry : stringMap.entrySet()) {
            System.out.println("entry = " + entry.getKey() + "--" + Arrays.toString(entry.getValue()));
        }
    }

    @Test
    public void mulTiAggregation() {
        Map<String, Number[]> stringMap = api.mulTiAggregation("2018-11-21 00:10", "2018-11-22 15:30");
        System.out.println("stringMap = " + stringMap);
        for (Map.Entry<String, Number[]> entry : stringMap.entrySet()) {
            System.out.println("entry = " + entry.getKey() + "--" + Arrays.toString(entry.getValue()));
        }
    }


    @Test
    public void batchSaveTimeUrl() {
        ArrayList<LogData> objects = Lists.newArrayList();

        for (int i = 0; i < 3; i++) {
            LogData logData = new LogData();
            logData.setReqTime("2018-11-21 15:14");
//            logData.setUrl(IndexClient.MARATHON + "express" + i + IndexClient.MARATHON + "pickupservice" + IndexClient.MARATHON + "getTaskDetail");
            logData.setUrl("/express/deliveryservice");
            logData.setRequestTimes(30 + i);
            logData.setErrorTimes(10 + i);
            logData.setType(GroupType.URL_TIME);
            objects.add(logData);
        }

        api.batchSave(objects);
    }

    @Test
    public void sumRequestGroupByURL() {
        Map<String, Number[]> stringMap = api.sumRequestGroupByURL("2018-11-21 00:10", "2018-11-22 15:30", "/express/deliveryservice");
        for (Map.Entry<String, Number[]> entry : stringMap.entrySet()) {
            System.out.println("entry = " + entry.getKey() + "--" + Arrays.toString(entry.getValue()));
        }
    }

    @Test
    public void sumRequestGroupByURLByFilter() {
        Map<String, Number[]> stringMap = api.sumRequestGroupByURL("2018-11-21 00:10", "2018-11-22 15:30");
        for (Map.Entry<String, Number[]> entry : stringMap.entrySet()) {
            System.out.println("entry = " + entry.getKey() + "--" + Arrays.toString(entry.getValue()));
        }
    }

    @Test
    public void BATCHsaveSourceIP() {
        ArrayList<LogData> objects = Lists.newArrayList();

        for (int i = 0; i < 10; i++) {
            LogData logData = new LogData();
            logData.setReqTime("2018-11-21 14:1" + i);
//        logData.setUrl(IndexClient.MARATHON+"express"+IndexClient.MARATHON+"pickupservice"+IndexClient.MARATHON+"getTaskDetail");
            logData.setSourceIp("10.202.106.1" + i);
            logData.setRequestTimes(20);
            logData.setErrorTimes(5);
            logData.setType(GroupType.SOURCEIP);
            objects.add(logData);
        }
        api.batchSave(objects);

    }

    @Test
    public void groupBySourceIP() {
        Map<String, Number[]> stringMap = api.groupBySourceIP("2018-11-21 00:10", "2018-11-22 15:30");
        for (Map.Entry<String, Number[]> entry : stringMap.entrySet()) {
            System.out.println("entry = " + entry.getKey() + "--" + Arrays.toString(entry.getValue()));
        }
    }

    @Test
    public void groupBySourceIPFilterByIP() {
        Map<String, Number[]> stringMap = api.groupBySourceIP("2018-11-21 00:10", "2018-11-22 15:30", "10.202.106.19");
        for (Map.Entry<String, Number[]> entry : stringMap.entrySet()) {
            System.out.println("entry = " + entry.getKey() + "--" + Arrays.toString(entry.getValue()));
        }
    }
    @Test
    public void batchSaveDestIP() {
        ArrayList<LogData> objects = Lists.newArrayList();

        for (int i = 0; i < 10; i++) {
            LogData logData = new LogData();
            logData.setReqTime("2018-12-22 14:1" + i);
//        logData.setUrl(IndexClient.MARATHON+"express"+IndexClient.MARATHON+"pickupservice"+IndexClient.MARATHON+"getTaskDetail");
            logData.setDestIp("10.202.108.1" + i);
            logData.setRequestTimes(20);
            logData.setErrorTimes(5);
            logData.setType(GroupType.DESTIP);
            objects.add(logData);

        }
        api.batchSave(objects);

    }

    @Test
    public void groupByDestIP() {

        String[] strings = {"log_2018-12-26"};
        List<RowBean> pageData = api.findPageData(GroupType.DESTIP.toString(), SearchClause.newClause(), 20, 0, strings);
        System.out.println("pageData = " + pageData);
        Date date = new Date(1545372840000L);
        System.out.println("date = " + date);
    }

    @Test
    public void groupByURL() {
        String[] strings = {"log_2018-12-29"};
        List<RowBean> pageData = api.findPageData(GroupType.URL_TYPE.toString(), SearchClause.newClause(), 20, 0, strings);
        System.out.println("pageData = " + pageData);
        Date date = new Date(1545372840000L);
        System.out.println("date = " + date);
    }

    @Test
    public void groupByDestIP1() {
        long starTm = System.currentTimeMillis();

        System.out.println("(System.currentTimeMillis()-starTm) = " + (System.currentTimeMillis() - starTm));

        starTm = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            Map<String, Number[]> stringMap = api.groupByDestIP("2018-12-21 14:10", "2018-12-21 15:30");
            for (Map.Entry<String, Number[]> entry : stringMap.entrySet()) {
                System.out.println("entry = " + entry.getKey() + "--" + Arrays.toString(entry.getValue()));
            }
        }
        System.out.println("(System.currentTimeMillis()-starTm) = " + (System.currentTimeMillis() - starTm));

    }


    @Test
    public void groupByDestIP1ByFilter() {
        Map<String, Number[]> stringMap = api.groupByDestIP("2018-12-21 14:10", "2018-12-21 15:30", "10.202.108.15");
        for (Map.Entry<String, Number[]> entry : stringMap.entrySet()) {
            System.out.println("entry = " + entry.getKey() + "--" + Arrays.toString(entry.getValue()));
        }
    }
}