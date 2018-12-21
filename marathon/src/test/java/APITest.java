import com.sf.marathon.np.index.api.API;
import com.sf.marathon.np.index.api.GroupType;
import com.sf.marathon.np.index.api.domain.LogData;
import com.sf.marathon.np.index.clause.SearchClause;
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

    @Test
    public void save() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("10.203.97.8:9300");

        LogData logData = new LogData();
        logData.setReqTime("2018-12-25 14:14");
        logData.setUrl("/express/pickupservice/confirm/getTaskDetail");
        logData.setMaxReponseTime(3.51d);
        logData.setMinReponseTime(2.16d);
        logData.setType(GroupType.URL_TYPE);
        new API().save(logData);
    }

    @Test
    public void mulTiAggregation() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("10.203.97.8:9300");
        API api = new API();
        Map<String, Number[]> stringMap = api.mulTiAggregation("2018-12-25","2018-12-21 14:10","2018-12-21 15:30");
        System.out.println("stringMap = " + stringMap);
    }

    @Test
    public void groupByDestIP() {
        EsConfig.getInstance().setClusterName("elasticsearch");
        EsConfig.getInstance().setEsUrl("10.203.97.8:9300");
        API api = new API();
        String[] strings = {"log_2018-12-25"};
        List<RowBean> pageData = api.findPageData(GroupType.URL_TYPE.toString(), SearchClause.newClause(), 20, 0, strings);
        System.out.println("pageData = " + pageData);
        Date date = new Date(1545372840000L);
        System.out.println("date = " + date);
    }
}