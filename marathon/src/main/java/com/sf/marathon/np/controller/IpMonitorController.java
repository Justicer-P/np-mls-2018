package com.sf.marathon.np.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.marathon.np.common.IpType;
import com.sf.marathon.np.controller.vo.req.IpMonitorReq;
import com.sf.marathon.np.controller.vo.resp.IpMonitorResp;
import com.sf.marathon.np.controller.vo.resp.RestResponse;
import com.sf.marathon.np.service.IIpMonitorService;

@RequestMapping("/ipMonitor")
@Controller
public class IpMonitorController extends BaseController{
	
	@Autowired
	private IIpMonitorService ipMonitorService;
	
	@PostMapping("/firstSourceIpMonitor")
	@ResponseBody
	public RestResponse<IpMonitorResp> firstSourceIpMonitor(IpMonitorReq req) {
		return handle(r -> {
			try {
				r.setResult(ipMonitorService.ipMonitor(IpType.SOURCE_IP, req, true));
			} catch (Exception e) {
				r.setMsg(e.getMessage());
			}
		});
	}
	
	@PostMapping("/sourceIpMonitor")
	@ResponseBody
	public RestResponse<IpMonitorResp> sourceIpMonitor(IpMonitorReq req) {
		return handle(r -> {
			try {
				r.setResult(ipMonitorService.ipMonitor(IpType.SOURCE_IP, req, false));
			} catch (Exception e) {
				r.setMsg(e.getMessage());
			}
		});
	}
	
	@PostMapping("/firstDestIpMonitor")
	@ResponseBody
	public RestResponse<IpMonitorResp> firstDestIpMonitor(IpMonitorReq req) {
		return handle(r -> {
			try {
				r.setResult(ipMonitorService.ipMonitor(IpType.DEST_IP, req, true));
			} catch (Exception e) {
				r.setMsg(e.getMessage());
			}
		});
	}
	
	@PostMapping("/destIpMonitor")
	@ResponseBody
	public RestResponse<IpMonitorResp> destIpMonitor(IpMonitorReq req) {
		return handle(r -> {
			try {
				r.setResult(ipMonitorService.ipMonitor(IpType.DEST_IP, req, false));
			} catch (Exception e) {
				r.setMsg(e.getMessage());
			}
		});
	}
	
	@PostMapping("/realTimeMonitorWithAllIps")
	@ResponseBody
	public RestResponse<IpMonitorResp> realTimeMonitorWithAllIps(IpMonitorReq req) {
		return handle(r -> r.setResult(ipMonitorService.realTimeMonitorWithAllIps(req)));
	}
	
}