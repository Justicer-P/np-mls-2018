package com.sf.marathon.np.service;

import com.sf.marathon.np.controller.vo.req.IpMonitorReq;
import com.sf.marathon.np.controller.vo.resp.AllIpsResp;
import com.sf.marathon.np.controller.vo.resp.IpMonitorResp;

public interface IIpMonitorService {
	
	public AllIpsResp getAllIps();
	
	public IpMonitorResp ipMonitor(String type, IpMonitorReq req, boolean isFirst) throws Exception;
	
	public IpMonitorResp realTimeMonitor(IpMonitorReq req);

	public IpMonitorResp realTimeMonitorWithAllIps(IpMonitorReq req);

}