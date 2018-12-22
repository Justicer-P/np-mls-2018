package com.sf.marathon.np.service;

import com.sf.marathon.np.controller.vo.req.IpMonitorReq;
import com.sf.marathon.np.controller.vo.resp.IpMonitorResp;

public interface IIpMonitorService {
	
	public IpMonitorResp ipMonitor(String type, IpMonitorReq req, boolean isFirst) throws Exception;
	
	public IpMonitorResp realTimeMonitorWithAllIps(IpMonitorReq req);

}