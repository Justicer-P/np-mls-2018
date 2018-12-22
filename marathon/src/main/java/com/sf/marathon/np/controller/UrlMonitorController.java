package com.sf.marathon.np.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.marathon.np.common.UrlMonitorType;
import com.sf.marathon.np.controller.vo.req.UrlMonitorReq;
import com.sf.marathon.np.controller.vo.resp.RestResponse;
import com.sf.marathon.np.controller.vo.resp.UrlMonitorResp;
import com.sf.marathon.np.service.IUrlMonitorService;

@RequestMapping("/urlMonitor")
@Controller
public class UrlMonitorController extends BaseController{
	
	@Autowired
	private IUrlMonitorService urlMonitorService;
	
	@PostMapping("/firstUrlMonitor")
	@ResponseBody
	public RestResponse<UrlMonitorResp> firstUrlMonitor(UrlMonitorReq req) {
		return handle(r -> {
			try {
//				r.setResult(urlMonitorService.firstUrlMonitor(req));
				r.setResult(urlMonitorService.urlMonitor(req, UrlMonitorType.URL_MONITOR, true));
			} catch (Exception e) {
				r.setMsg(e.getMessage());
			}
		});
	}
	
	@PostMapping("/urlMonitor")
	@ResponseBody
	public RestResponse<UrlMonitorResp> urlMonitor(UrlMonitorReq req) {
		return handle(r -> {
			try {
//				r.setResult(urlMonitorService.urlMonitor(req));
				r.setResult(urlMonitorService.urlMonitor(req, UrlMonitorType.URL_MONITOR, false));
			} catch (Exception e) {
				r.setMsg(e.getMessage());
			}
		});
	}
	
	@PostMapping("/firstUrlMonitorSummary")
	@ResponseBody
	public RestResponse<UrlMonitorResp> firstUrlMonitorSummary(UrlMonitorReq req) {
		return handle(r -> {
			try {
//				r.setResult(urlMonitorService.firstUrlMonitorSummary(req));
				r.setResult(urlMonitorService.urlMonitor(req, UrlMonitorType.URL_SUM_MONITOR, true));
			} catch (Exception e) {
				r.setMsg(e.getMessage());
			}
		});
	}
	
	@PostMapping("/urlMonitorSummary")
	@ResponseBody
	public RestResponse<UrlMonitorResp> urlMonitorSummary(UrlMonitorReq req) {
		return handle(r -> {
			try {
//				r.setResult(urlMonitorService.urlMonitorSummary(req));
				r.setResult(urlMonitorService.urlMonitor(req, UrlMonitorType.URL_SUM_MONITOR, false));
			} catch (Exception e) {
				r.setMsg(e.getMessage());
			}
		});
	}
	
}
