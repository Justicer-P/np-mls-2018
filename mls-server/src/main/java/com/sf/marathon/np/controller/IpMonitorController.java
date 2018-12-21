package com.sf.marathon.np.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.marathon.np.controller.vo.req.IpMonitorReq;
import com.sf.marathon.np.controller.vo.resp.AllIpsResp;
import com.sf.marathon.np.controller.vo.resp.IpMonitorResp;
import com.sf.marathon.np.controller.vo.resp.RestResponse;
import com.sf.marathon.np.service.IIpMonitorService;

@RequestMapping("/ipMonitor")
@Controller
public class IpMonitorController extends BaseController{
	
	@Autowired
	private IIpMonitorService ipMonitorService;
	
	@PostMapping("/getAllIps")
	@ResponseBody
	public RestResponse<AllIpsResp> getAllIps() {
		return handle(r -> r.setResult(ipMonitorService.getAllIps()));
	}
	
	@PostMapping("/sourceIpMonitor")
	@ResponseBody
	public RestResponse<IpMonitorResp> sourceIpMonitor(@RequestBody IpMonitorReq req) {
		return handle(r -> {
			try {
				r.setResult(ipMonitorService.sourceIpMonitor(req));
			} catch (Exception e) {
				r.setMsg(e.getMessage());
			}
		});
	}
	
	@PostMapping("/destIpMonitor")
	@ResponseBody
	public RestResponse<IpMonitorResp> destIpMonitor(@RequestBody IpMonitorReq req) {
		return handle(r -> {
			try {
				r.setResult(ipMonitorService.destIpMonitor(req));
			} catch (Exception e) {
				r.setMsg(e.getMessage());
			}
		});
	}
	
	@PostMapping("/realTimeMonitor")
	@ResponseBody
	public RestResponse<IpMonitorResp> realTimeMonitor(@RequestBody IpMonitorReq req) {
		return handle(r -> r.setResult(ipMonitorService.realTimeMonitor(req)));
	}
	
}