import com.sf.marathon.np.index.api.API;
import com.sf.marathon.np.index.api.GroupType;
import com.sf.marathon.np.index.api.domain.LogData;
import com.sf.marathon.np.index.clause.SearchClause;
import com.sf.marathon.np.index.client.IndexClient;
import com.sf.marathon.np.index.domain.EsConfig;
import com.sf.marathon.np.index.domain.RowBean;
import org.junit.Test;

import java.util.Date;
import java.util.List;
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
public class APITest {

    public static void main(String[] args) {
        System.out.println("args = " + args);
    }

    @Test
    public void saveURL() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("10.202.39.151:9300");

        LogData logData = new LogData();
        logData.setReqTime("2018-12-26 14:14");
        logData.setUrl(IndexClient.MARATHON + "express" + IndexClient.MARATHON + "pickupservice" + IndexClient.MARATHON + "getTaskDetail");
        logData.setMaxReponseTime(3.51d);
        logData.setMinReponseTime(2.16d);
        logData.setType(GroupType.URL_TYPE);
        new API().save(logData);
    }

    @Test
    public void saveSourceIP() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("10.202.39.151:9300");
        for (int i = 0; i < 10; i++) {
            LogData logData = new LogData();
            logData.setReqTime("2018-12-26 14:14");
//        logData.setUrl(IndexClient.MARATHON+"express"+IndexClient.MARATHON+"pickupservice"+IndexClient.MARATHON+"getTaskDetail");
            logData.setSourceIp("10.202.106.1" + i);
            logData.setRequestTimes(20);
            logData.setErrorTimes(5);
            logData.setType(GroupType.SOURCEIP);
            new API().save(logData);
        }

    }

    @Test
    public void saveDestIP() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("10.202.39.151:9300");
        for (int i = 0; i < 10; i++) {
            LogData logData = new LogData();
            logData.setReqTime("2018-12-26 14:14");
//        logData.setUrl(IndexClient.MARATHON+"express"+IndexClient.MARATHON+"pickupservice"+IndexClient.MARATHON+"getTaskDetail");
            logData.setDestIp("10.202.108.1" + i);
            logData.setRequestTimes(20);
            logData.setErrorTimes(5);
            logData.setType(GroupType.DESTIP);
            new API().save(logData);
        }

    }

    @Test
    public void mulTiAggregation() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("10.202.39.151:9300");
        API api = new API();
        Map<String, Number[]> stringMap = api.mulTiAggregation("2018-12-26 14:10", "2018-12-26 15:30");
        System.out.println("stringMap = " + stringMap);
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
    public void sumRequestGroupByURL() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("10.202.39.151:9300");
        API api = new API();
        Map<String, Number[]> stringMap = api.sumRequestGroupByURL("2018-12-26 14:10", "2018-12-26 15:30");
        System.out.println("stringMap = " + stringMap);
    }

    @Test
    public void groupBySourceIP() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("10.202.39.151:9300");
        API api = new API();
        Map<String, Number[]> stringMap = api.groupBySourceIP("2018-12-26 14:10", "2018-12-26 15:30");
        System.out.println("stringMap = " + stringMap);
    }

    @Test
    public void groupByDestIP1() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("10.202.39.151:9300");
        API api = new API();
        Map<String, Number[]> stringMap = api.groupByDestIP("2018-12-26 14:10", "2018-12-26 15:30");
        System.out.println("stringMap = " + stringMap);
    }
}