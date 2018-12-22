import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.sf.marathon.np.common.UrlMonitorType;
import com.sf.marathon.np.controller.vo.req.UrlMonitorReq;
import com.sf.marathon.np.service.IUrlMonitorService;

@RunWith(SpringRunner.class)
public class UrlMonitorTest {
	
	@Autowired
	private IUrlMonitorService urlMonitorService;
	
	private UrlMonitorReq req;
	
	@Before
	public void before() {
		req = new UrlMonitorReq();
		req.setBeginTime("");
		req.setUrl("");
		req.setEndTime("");
	}
	
	@Test
	public void testUrlMonitor() {
//		Mockito.when(methodCall)
		urlMonitorService.urlMonitor(req, UrlMonitorType.URL_MONITOR, true);
	}
	
}
