package com.sf.marathon.np.service.impl;

import org.springframework.stereotype.Service;

import com.sf.marathon.np.controller.vo.resp.AllUrlsResp;
import com.sf.marathon.np.controller.vo.resp.UrlMonitorResp;
import com.sf.marathon.np.service.IUrlMonitorService;

@Service
public class UrlMonitorService implements IUrlMonitorService{

	@Override
	public AllUrlsResp getAllUrls() {
		AllUrlsResp resp = new AllUrlsResp();
		resp.setUrls(null);
		return resp;
	}

	@Override
	public UrlMonitorResp urlMonitor() {
		UrlMonitorResp resp = new UrlMonitorResp();
		return resp;
	}

	@Override
	public UrlMonitorResp urlMonitorSummary() {
		UrlMonitorResp resp = new UrlMonitorResp();
		return resp;
	}

}
