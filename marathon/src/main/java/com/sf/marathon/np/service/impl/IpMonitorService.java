package com.sf.marathon.np.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.marathon.np.common.IpType;
import com.sf.marathon.np.controller.vo.req.IpMonitorReq;
import com.sf.marathon.np.controller.vo.resp.AllIpsResp;
import com.sf.marathon.np.controller.vo.resp.IpMonitorResp;
import com.sf.marathon.np.index.api.API;
import com.sf.marathon.np.service.IIpMonitorService;
import com.sf.marathon.np.util.TimeUtil;

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
		List<String> ipRequestCount = new ArrayList<>();
		List<String> ipFailCount = new ArrayList<>();
		List<String> xAxis = new ArrayList<>();
		Map<String, Number[]> result;
		switch (type) {
		case IpType.SOURCE_IP:
			if (isFirst) {
				result = api.groupBySourceIP(req.getBeginTime(), req.getEndTime());
				firstIpMonitor(resp, ipRequestCount, ipFailCount, xAxis, result);
			} else {
				result = api.groupBySourceIP(req.getBeginTime(), req.getEndTime(), req.getSourceIp());
				ipMonitor(req, resp, ipRequestCount, ipFailCount, xAxis, result);
			}
			break;
		case IpType.DEST_IP:
			if (isFirst) {
				result = api.groupByDestIP(req.getBeginTime(), req.getEndTime());
				firstIpMonitor(resp, ipRequestCount, ipFailCount, xAxis, result);
			} else {
				result = api.groupByDestIP(req.getBeginTime(), req.getEndTime(), req.getDestIp());
				ipMonitor(req, resp, ipRequestCount, ipFailCount, xAxis, result);
			}
			break;
		default:
			throw new RuntimeException("unsupported type string!");
		}
		return resp;
	}

	private void ipMonitor(IpMonitorReq req, IpMonitorResp resp, List<String> ipRequestCount, List<String> ipFailCount,
			List<String> xAxis, Map<String, Number[]> result) {
		Map<String, Number[]> sortedMap = new TreeMap<>((m1, m2) -> {
			return m1.substring(0, m1.indexOf("~")).compareTo(m2.substring(0, m2.indexOf("~")));
		});
		sortedMap.putAll(result);
		sortedMap.forEach((k, v) -> {
			int index = k.indexOf("~");
			setLists(ipRequestCount, ipFailCount, xAxis, k, v, index);
		});
		resp.setIp(req.getIp());
	}

	private void firstIpMonitor(IpMonitorResp resp, List<String> ipRequestCount, List<String> ipFailCount,
			List<String> xAxis, Map<String, Number[]> result) {
		Set<String> srcIps = new HashSet<>();
		final boolean[] flag = new boolean[] { true };
		final String[] ip = new String[] { "" };
		result.forEach((k, v) -> {
			int index = k.indexOf("~");
			String tmpUrl = k.substring(index + 1);
			if (flag[0]) { // 第一个ip
				ip[0] = tmpUrl;
				setLists(ipRequestCount, ipFailCount, xAxis, k, v, index);
				srcIps.add(tmpUrl);
				flag[0] = false;
			} else if (ip[0].equals(tmpUrl)) { // 与第一个ip相等
				setLists(ipRequestCount, ipFailCount, xAxis, k, v, index);
			} else {
				srcIps.add(tmpUrl);
			}
		});
		resp.setIp(ip[0]);
		resp.setSrcOrDestIps(new ArrayList<>(srcIps));
	}
	
	private void setLists(List<String> ipRequestCount, List<String> ipFailCount, List<String> xAxis, String k,
			Number[] v, int index) {
		String time = TimeUtil.formatLong("yyyy-MM-dd HH:mm", Long.valueOf(k.substring(0, index)));
		xAxis.add(time);
		ipRequestCount.add(String.valueOf(v[0]));
		ipFailCount.add(String.valueOf(v[1]));
	}

	@Override
	public IpMonitorResp realTimeMonitor(IpMonitorReq req) {
		// TODO
		IpMonitorResp resp = new IpMonitorResp();
		return resp;
	}

}
