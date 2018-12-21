import com.sf.marathon.np.index.api.API;
import com.sf.marathon.np.index.api.GroupType;
import com.sf.marathon.np.index.api.domain.LogData;
import com.sf.marathon.np.index.clause.SearchClause;
import com.sf.marathon.np.index.client.ElasticClient;
import com.sf.marathon.np.index.domain.EsConfig;
import com.sf.marathon.np.index.domain.RowBean;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.collect.Lists;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

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
public class APITest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println("args = " + args);
        String utf8 = URLEncoder.encode("/express/pickupservice", "UTF8");
        System.out.println("utf8 = " + utf8);

        Date date = new Date(1545804720000l);
        System.out.println("date = " + date);
    }

    @Test
    public void saveURL() throws UnsupportedEncodingException {
        EsConfig.getInstance().setClusterName("elasticsearch");
//        EsConfig.getInstance().setEsUrl("10.203.97.8:9300");
        EsConfig.getInstance().setEsUrl("localhost:9300");

        LogData logData = new LogData();
        logData.setReqTime("2018-11-30 14:14");
//        String utf8 = URLEncoder.encode("/express/pickupservice", "UTF8");
        logData.setUrl("/express/pickupservice");
        logData.setMaxReponseTime(3.51d);
        logData.setMinReponseTime(2.16d);
        logData.setAvgReponseTime(3.5d);
        logData.setNinePercentResponseTime(5.4d);
        logData.setType(GroupType.URL_TYPE);
        new API().save(logData);
    }

    @Test
    public void batchSaveUrl() {
        EsConfig.getInstance().setClusterName("elasticsearch");
//        EsConfig.getInstance().setEsUrl("10.202.39.151:9300");
        EsConfig.getInstance().setEsUrl("localhost:9300");
        ArrayList<LogData> objects = Lists.newArrayList();

        for (int i = 0; i < 3; i++) {
            LogData logData = new LogData();
            logData.setReqTime("2018-11-29 14:14");
//            logData.setUrl(IndexClient.MARATHON + "express" + i + IndexClient.MARATHON + "pickupservice" + IndexClient.MARATHON + "getTaskDetail");
            logData.setUrl("/express/pickservice");
            logData.setMaxReponseTime(4.51d);
            logData.setMinReponseTime(3.16d);
            logData.setAvgReponseTime(2.5d);
            logData.setNinePercentResponseTime(6.4d);
            logData.setType(GroupType.URL_TYPE);
            objects.add(logData);
        }

        new API().batchSave(objects);
    }

    @Test
    public void batchSaveTimeUrl() {
        EsConfig.getInstance().setClusterName("elasticsearch");
//        EsConfig.getInstance().setEsUrl("10.202.39.151:9300");
        EsConfig.getInstance().setEsUrl("localhost:9300");
        ArrayList<LogData> objects = Lists.newArrayList();

        for (int i = 0; i < 3; i++) {
            LogData logData = new LogData();
            logData.setReqTime("2018-11-28 14:14");
//            logData.setUrl(IndexClient.MARATHON + "express" + i + IndexClient.MARATHON + "pickupservice" + IndexClient.MARATHON + "getTaskDetail");
            logData.setUrl("/express/deliveryservice");
            logData.setRequestTimes(30 + i);
            logData.setErrorTimes(10 + i);
            logData.setType(GroupType.URL_TIME);
            objects.add(logData);
        }

        new API().batchSave(objects);
    }

    @Test
    public void BATCHsaveSourceIP() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("localhost:9300");
        ArrayList<LogData> objects = Lists.newArrayList();

        for (int i = 0; i < 10; i++) {
            LogData logData = new LogData();
            logData.setReqTime("2018-12-26 14:1" + i);
//        logData.setUrl(IndexClient.MARATHON+"express"+IndexClient.MARATHON+"pickupservice"+IndexClient.MARATHON+"getTaskDetail");
            logData.setSourceIp("10.202.106.1" + i);
            logData.setRequestTimes(20);
            logData.setErrorTimes(5);
            logData.setType(GroupType.SOURCEIP);
            objects.add(logData);
        }
        new API().batchSave(objects);

    }

    @Test
    public void batchSaveDestIP() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("localhost:9300");

        ArrayList<LogData> objects = Lists.newArrayList();

        for (int i = 0; i < 10; i++) {
            LogData logData = new LogData();
            logData.setReqTime("2018-12-21 14:1" + i);
//        logData.setUrl(IndexClient.MARATHON+"express"+IndexClient.MARATHON+"pickupservice"+IndexClient.MARATHON+"getTaskDetail");
            logData.setDestIp("10.202.108.1" + i);
            logData.setRequestTimes(20);
            logData.setErrorTimes(5);
            logData.setType(GroupType.DESTIP);
            objects.add(logData);

        }
        new API().batchSave(objects);

    }

    @Test
    public void mulTiAggregation() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("localhost:9300");
        API api = new API();//2018-11-29 14:14
        Map<String, Number[]> stringMap = api.mulTiAggregation("2018-11-29 14:10", "2018-11-29 15:30");
        System.out.println("stringMap = " + stringMap);
        for (Map.Entry<String, Number[]> entry : stringMap.entrySet()) {
            System.out.println("entry = " + entry.getKey() + "--" + Arrays.toString(entry.getValue()));
        }
    }

    @Test
    public void mulTiAggregationByUrl() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("localhost:9300");
        API api = new API();//2018-11-29 14:14
        Map<String, Number[]> stringMap = api.mulTiAggregation("2018-11-29 14:10", "2018-11-29 15:30", "/express/deliveryservice");
        System.out.println("stringMap = " + stringMap);
        for (Map.Entry<String, Number[]> entry : stringMap.entrySet()) {
            System.out.println("entry = " + entry.getKey() + "--" + Arrays.toString(entry.getValue()));
        }
    }


    @Test
    public void groupByDestIP() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("10.202.39.151:9300");
        API api = new API();
        String[] strings = {"log_2018-12-26"};
        List<RowBean> pageData = api.findPageData(GroupType.DESTIP.toString(), SearchClause.newClause(), 20, 0, strings);
        System.out.println("pageData = " + pageData);
        Date date = new Date(1545372840000L);
        System.out.println("date = " + date);
    }

    @Test
    public void groupByURL() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("localhost:9300");
        API api = new API();
        String[] strings = {"log_2018-12-29"};
        List<RowBean> pageData = api.findPageData(GroupType.URL_TYPE.toString(), SearchClause.newClause(), 20, 0, strings);
        System.out.println("pageData = " + pageData);
        Date date = new Date(1545372840000L);
        System.out.println("date = " + date);
    }

    @Test
    public void sumRequestGroupByURL() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("localhost:9300");
        API api = new API();
        Map<String, Number[]> stringMap = api.sumRequestGroupByURL("2018-11-28 14:10", "2018-11-28 15:30");
        for (Map.Entry<String, Number[]> entry : stringMap.entrySet()) {
            System.out.println("entry = " + entry.getKey() + "--" + Arrays.toString(entry.getValue()));
        }
    }

    @Test
    public void sumRequestGroupByURLByFilter() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("localhost:9300");
        API api = new API();
        Map<String, Number[]> stringMap = api.sumRequestGroupByURL("2018-11-28 14:10", "2018-11-28 15:30", "/express/pickservice");
        for (Map.Entry<String, Number[]> entry : stringMap.entrySet()) {
            System.out.println("entry = " + entry.getKey() + "--" + Arrays.toString(entry.getValue()));
        }
    }

    @Test
    public void groupBySourceIP() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("localhost:9300");
        API api = new API();
        Map<String, Number[]> stringMap = api.groupBySourceIP("2018-12-26 14:10", "2018-12-26 15:30");
        for (Map.Entry<String, Number[]> entry : stringMap.entrySet()) {
            System.out.println("entry = " + entry.getKey() + "--" + Arrays.toString(entry.getValue()));
        }
    }

    @Test
    public void groupBySourceIPFilterByIP() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("localhost:9300");
        API api = new API();
        Map<String, Number[]> stringMap = api.groupBySourceIP("2018-12-26 14:10", "2018-12-26 15:30", "10.202.106.19");
        for (Map.Entry<String, Number[]> entry : stringMap.entrySet()) {
            System.out.println("entry = " + entry.getKey() + "--" + Arrays.toString(entry.getValue()));
        }
    }

    @Test
    public void groupByDestIP1() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("localhost:9300");
        API api = new API();
        long starTm = System.currentTimeMillis();

        Client client = ElasticClient.instance.getClient();
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
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("localhost:9300");
        API api = new API();
        Map<String, Number[]> stringMap = api.groupByDestIP("2018-12-21 14:10", "2018-12-21 15:30", "10.202.108.15");
        for (Map.Entry<String, Number[]> entry : stringMap.entrySet()) {
            System.out.println("entry = " + entry.getKey() + "--" + Arrays.toString(entry.getValue()));
        }
    }
}