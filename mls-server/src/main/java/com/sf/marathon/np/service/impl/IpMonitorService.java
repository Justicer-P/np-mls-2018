package com.sf.marathon.np.service.impl;

import org.springframework.stereotype.Service;

import com.sf.marathon.np.controller.vo.req.IpMonitorReq;
import com.sf.marathon.np.controller.vo.resp.AllIpsResp;
import com.sf.marathon.np.controller.vo.resp.IpMonitorResp;
import com.sf.marathon.np.service.IIpMonitorService;
import com.sf.marathon.np.util.TimeUtil;

@Service
public class IpMonitorService implements IIpMonitorService{

	@Override
	public AllIpsResp getAllIps() {
		return null;
	}

	@Override
	public IpMonitorResp sourceIpMonitor(IpMonitorReq req) throws Exception {
		IpMonitorResp resp = new IpMonitorResp();
		resp.setxAxis(TimeUtil.getIntervalTimeList(req.getBeginTime(), req.getEndTime(), 1));
		return resp;
	}

	@Override
	public IpMonitorResp destIpMonitor(IpMonitorReq req) throws Exception {
		IpMonitorResp resp = new IpMonitorResp();
		resp.setxAxis(TimeUtil.getIntervalTimeList(req.getBeginTime(), req.getEndTime(), 1));
		return resp;
	}

	@Override
	public IpMonitorResp realTimeMonitor(IpMonitorReq req) {
		IpMonitorResp resp = new IpMonitorResp();
		return resp;
	}

}
