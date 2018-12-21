package com.sf.marathon.np.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.marathon.np.controller.vo.resp.AllUrlsResp;
import com.sf.marathon.np.controller.vo.resp.RestResponse;
import com.sf.marathon.np.controller.vo.resp.UrlMonitorResp;
import com.sf.marathon.np.service.IUrlMonitorService;

@RequestMapping("/urlMonitor")
@Controller
public class UrlMonitorController extends BaseController{
	
	@Autowired
	private IUrlMonitorService urlMonitorService;
	
	@PostMapping("/getAllUrls")
	@ResponseBody
	public RestResponse<AllUrlsResp> getAllUrls() {
		return handle(r -> r.setResult(urlMonitorService.getAllUrls()));
	}
	
	@PostMapping("/urlMonitor")
	@ResponseBody
	public RestResponse<UrlMonitorResp> urlMonitor() {
		return handle(r -> r.setResult(urlMonitorService.urlMonitor()));
	}
	
	@PostMapping("/urlMonitorSummary")
	@ResponseBody
	public RestResponse<UrlMonitorResp> urlMonitorSummary() {
		return handle(r -> r.setResult(urlMonitorService.urlMonitorSummary()));
	}
	
}
