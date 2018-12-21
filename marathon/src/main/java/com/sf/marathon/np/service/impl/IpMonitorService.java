package com.sf.marathon.np.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.marathon.np.common.IpType;
import com.sf.marathon.np.controller.vo.req.IpMonitorReq;
import com.sf.marathon.np.controller.vo.resp.AllIpsResp;
import com.sf.marathon.np.controller.vo.resp.IpMonitorResp;
import com.sf.marathon.np.index.api.API;
import com.sf.marathon.np.service.IIpMonitorService;

@Service
public class IpMonitorService implements IIpMonitorService {

	@Autowired
	private API api;

	@Override
	public AllIpsResp getAllIps() {
		return null;
	}

	@Override
	public IpMonitorResp ipMonitor(String type, IpMonitorReq req, boolean isFirst) throws Exception {
		IpMonitorResp resp = new IpMonitorResp();
		List<String> srcOrDestIps = new ArrayList<>();
		List<String> ipRequestCount = new ArrayList<>();
		List<String> ipFailCount = new ArrayList<>();
		Map<String, Number[]> result;
		switch (type) {
		case IpType.SOURCE_IP:
			if (isFirst) {
				result = api.groupBySourceIP(req.getBeginTime(), req.getEndTime());
			} else {
				result = api.groupBySourceIP(req.getBeginTime(), req.getEndTime(), req.getSourceIp());
			}
			// TODO result set lists
			break;
		case IpType.DEST_IP:
			if (isFirst) {
				result = api.groupByDestIP(req.getBeginTime(), req.getEndTime());
			} else {
				result = api.groupByDestIP(req.getBeginTime(), req.getEndTime(), req.getDestIp());
			}
			// TODO result set lists
			break;
		default:
			throw new RuntimeException("unsupported type string!");
		}
		resp.setIp(req.getIp());
		resp.setSrcOrDestIps(srcOrDestIps);
		resp.setIpRequestCount(ipRequestCount);
		resp.setIpFailCount(ipFailCount);
		return resp;
	}

	@Override
	public IpMonitorResp realTimeMonitor(IpMonitorReq req) {
		// TODO
		IpMonitorResp resp = new IpMonitorResp();
		return resp;
	}

}
