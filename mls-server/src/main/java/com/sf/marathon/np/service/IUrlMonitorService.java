package com.sf.marathon.np.service;

import com.sf.marathon.np.controller.vo.req.UrlMonitorReq;
import com.sf.marathon.np.controller.vo.resp.AllUrlsResp;
import com.sf.marathon.np.controller.vo.resp.UrlMonitorResp;

public interface IUrlMonitorService {

	public AllUrlsResp getAllUrls();

	public UrlMonitorResp urlMonitor(UrlMonitorReq req) throws Exception;

	public UrlMonitorResp urlMonitorSummary(UrlMonitorReq req) throws Exception;

}