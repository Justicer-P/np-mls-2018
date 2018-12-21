package com.sf.marathon.np.service.impl;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.marathon.np.controller.vo.req.UrlMonitorReq;
import com.sf.marathon.np.controller.vo.resp.AllUrlsResp;
import com.sf.marathon.np.controller.vo.resp.UrlMonitorResp;
import com.sf.marathon.np.index.api.API;
import com.sf.marathon.np.service.IUrlMonitorService;
import com.sf.marathon.np.util.TimeUtil;

@Service
public class UrlMonitorService implements IUrlMonitorService{
	
	@Autowired
	private API api;

	@Override
	public AllUrlsResp getAllUrls() {
		return null;
	}

	@Override
	public UrlMonitorResp urlMonitor(UrlMonitorReq req) throws ParseException {
		UrlMonitorResp resp = new UrlMonitorResp();
		resp.setxAxis(TimeUtil.getIntervalTimeList(req.getBeginTime(), req.getEndTime(), 1));
		return resp;
	}

	@Override
	public UrlMonitorResp urlMonitorSummary(UrlMonitorReq req) throws ParseException {
		UrlMonitorResp resp = new UrlMonitorResp();
		resp.setxAxis(TimeUtil.getIntervalTimeList(req.getBeginTime(), req.getEndTime(), 1));
		return resp;
	}

}
