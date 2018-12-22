package com.sf.marathon.np.service;

import com.sf.marathon.np.controller.vo.req.UrlMonitorReq;
import com.sf.marathon.np.controller.vo.resp.UrlMonitorResp;

public interface IUrlMonitorService {

	public UrlMonitorResp urlMonitor(UrlMonitorReq req, String type, boolean isFirst);

}