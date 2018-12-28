import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.sf.marathon.np.common.UrlMonitorType;
import com.sf.marathon.np.controller.vo.req.UrlMonitorReq;
import com.sf.marathon.np.index.api.API;
import com.sf.marathon.np.service.IUrlMonitorService;
import com.sf.marathon.np.service.impl.UrlMonitorService;

//@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
//@SpringBootConfiguration
public class UrlMonitorTest {
	
	@InjectMocks
	private IUrlMonitorService urlMonitorService = new UrlMonitorService();
	
	@Mock
	private API api;
	
	private UrlMonitorReq req;
	
	private static Map<String, Number[]> m1 = new HashMap<>();
	
	public static void main(String[] args) throws Exception {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2018-12-21 11:00").getTime());
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2018-12-21 11:01").getTime());
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2018-12-21 11:02").getTime());
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2018-12-21 11:03").getTime());
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2018-12-21 11:04").getTime());
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2018-12-21 11:05").getTime());
	}
	
	@Before
	public void before() {
		
		// 初始化mockito，与@RunWith(MockitoJUnitRunner.class)注解二选一即可
		MockitoAnnotations.initMocks(this);
		
		req = new UrlMonitorReq();
		req.setBeginTime("");
		req.setUrl("");
		req.setEndTime("");
		// es-client 封装的api的返回格式：“key：Long类型到时间（到分钟）～URL     value：{请求次数， 失败次数}”
		m1.put("1545361200000~1/abc/def/aaaa", new Number[] {5, 1});
		m1.put("1545361260000~1/abc/def/aaaa", new Number[] {6, 1});
		m1.put("1545361320000~1/abc/def/aaaa", new Number[] {7, 1});
		m1.put("1545361380000~1/abc/def/aaaa", new Number[] {8, 1});
		m1.put("1545361440000~1/abc/def/aaaa", new Number[] {9, 1});
		m1.put("1545361500000~1/abc/def/aaaa", new Number[] {10, 1});
		
		m1.put("1545361500000~2/abc/def/aaaa", new Number[] {10, 1});
		m1.put("1545361380000~2/abc/def/aaaa", new Number[] {10, 1});
		m1.put("1545361500000~2/abc/def/aaaa", new Number[] {10, 1});
		m1.put("1545361320000~2/abc/def/aaaa", new Number[] {10, 1});
	}
	
	@Test
	public void testUrlMonitor() {
		when(api.sumRequestGroupByURL(anyString(), anyString())).thenReturn(m1);
		System.out.println("------------------------------");
		System.out.println(urlMonitorService.urlMonitor(req, UrlMonitorType.URL_MONITOR, true));
		System.out.println("------------------------------");
	}
	
}
