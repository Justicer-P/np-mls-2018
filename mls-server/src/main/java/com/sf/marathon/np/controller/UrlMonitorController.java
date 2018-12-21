package com.sf.marathon.np.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.marathon.np.controller.vo.resp.AllUrlsResp;
import com.sf.marathon.np.controller.vo.resp.RestResponse;
import com.sf.marathon.np.controller.vo.resp.UrlMonitorResp;

@RequestMapping("/urlMonitor")
@Controller
public class UrlMonitorController extends BaseController{
	
	@PostMapping("/getAllUrls")
	@ResponseBody
	public RestResponse<AllUrlsResp> getAllUrls() {
		return handle(r -> r.setResult(null));
	}
	
	@PostMapping("/urlMonitor")
	@ResponseBody
	public RestResponse<UrlMonitorResp> urlMonitor() {
		return handle(r -> r.setResult(null));
	}
	
	@PostMapping("/urlMonitorSummary")
	@ResponseBody
	public RestResponse<UrlMonitorResp> urlMonitorSummary() {
		return handle(r -> r.setResult(null));
	}
	
}
