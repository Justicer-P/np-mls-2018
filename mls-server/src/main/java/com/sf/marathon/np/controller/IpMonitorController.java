package com.sf.marathon.np.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.marathon.np.controller.vo.req.IpMonitorReq;
import com.sf.marathon.np.controller.vo.resp.AllIpsResp;
import com.sf.marathon.np.controller.vo.resp.IpMonitorResp;
import com.sf.marathon.np.controller.vo.resp.RestResponse;

@RequestMapping("/ipMonitor")
@Controller
public class IpMonitorController extends BaseController{
	
	@PostMapping("/getAllIps")
	@ResponseBody
	public RestResponse<AllIpsResp> getAllIps() {
		return handle(r -> r.setResult(null));
	}
	
	@PostMapping("/sourceIpMonitor")
	@ResponseBody
	public RestResponse<IpMonitorResp> sourceIpMonitor(@RequestBody IpMonitorReq req) {
		return handle(r -> r.setResult(null));
	}
	
	@PostMapping("/destIpMonitor")
	@ResponseBody
	public RestResponse<IpMonitorResp> destIpMonitor(@RequestBody IpMonitorReq req) {
		return handle(r -> r.setResult(null));
	}
	
	@PostMapping("/realTimeMonitor")
	@ResponseBody
	public RestResponse<IpMonitorResp> realTimeMonitor(@RequestBody IpMonitorReq req) {
		return handle(r -> r.setResult(null));
	}
	
}