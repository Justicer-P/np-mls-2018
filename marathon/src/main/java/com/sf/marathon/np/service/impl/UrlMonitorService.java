package com.sf.marathon.np.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.marathon.np.controller.vo.req.UrlMonitorReq;
import com.sf.marathon.np.controller.vo.resp.AllUrlsResp;
import com.sf.marathon.np.controller.vo.resp.UrlMonitorResp;
import com.sf.marathon.np.index.api.API;
import com.sf.marathon.np.service.IUrlMonitorService;
import com.sf.marathon.np.util.TimeUtil;

@Service
public class UrlMonitorService implements IUrlMonitorService {

	@Autowired
	private API api;

	@Override
	public AllUrlsResp getAllUrls() {
		return null;
	}

	@Override
	public UrlMonitorResp urlMonitor(UrlMonitorReq req) throws ParseException {
		UrlMonitorResp resp = new UrlMonitorResp();
		Map<String, Number[]> result = api.sumRequestGroupByURL(req.getBeginTime(), req.getEndTime());
		Map<String, Number[]> sortedResult = new TreeMap<>((o1, o2) -> {
			return o1.substring(0, o1.indexOf("~")).compareTo(o2.substring(0, o1.indexOf("~")));
		});
		sortedResult.putAll(result);
		List<String> xAxis = new ArrayList<>();
		List<String> urlRequestCount = new ArrayList<>();
		List<String> urlFailCount = new ArrayList<>();
		Set<String> urls = new HashSet<>();
		sortedResult.forEach((k, v) -> {
			int index = k.indexOf("~");
			String time = TimeUtil.formatLong("yyyy-MM-dd HH:mm", Long.valueOf(k.substring(0, index)));
			xAxis.add(time);
			urls.add(k.substring(index + 1));
			urlRequestCount.add(String.valueOf(v[0]));
			urlFailCount.add(String.valueOf(v[1]));
		});
		resp.setUrls(new ArrayList<String>(urls));
		resp.setxAxis(xAxis);
		resp.setUrlRequestCount(urlRequestCount);
		resp.setUrlFailCount(urlFailCount);
		return resp;
	}

	@Override
	public UrlMonitorResp urlMonitorSummary(UrlMonitorReq req) throws ParseException {
		UrlMonitorResp resp = new UrlMonitorResp();
		Map<String, Number[]> result = api.mulTiAggregation(req.getBeginTime(), req.getEndTime());
		Map<String, Number[]> sortedResult = new TreeMap<>((o1, o2) -> {
			return o1.substring(0, o1.indexOf("~")).compareTo(o2.substring(0, o1.indexOf("~")));
		});
		sortedResult.putAll(result);
		List<String> xAxis = new ArrayList<>();
		List<String> longestRespTime = new ArrayList<>();
		List<String> shortestRespTime = new ArrayList<>();
		List<String> avgRespTime = new ArrayList<>();
		List<String> ninetyPercentRespTime = new ArrayList<>();
		Set<String> urls = new HashSet<>();
		sortedResult.forEach((k, v) -> {
			int index = k.indexOf("~");
			String time = TimeUtil.formatLong("yyyy-MM-dd HH:mm", Long.valueOf(k.substring(0, index)));
			xAxis.add(time);
			urls.add(k.substring(index + 1));
			longestRespTime.add(String.valueOf(v[0]));
			shortestRespTime.add(String.valueOf(v[1]));
			avgRespTime.add(String.valueOf(v[2]));
			ninetyPercentRespTime.add(String.valueOf(v[2]));
		});
		resp.setUrls(new ArrayList<String>(urls));
		resp.setxAxis(xAxis);
		resp.setLongestRespTime(longestRespTime);
		resp.setShortestRespTime(shortestRespTime);
		resp.setAvgRespTime(avgRespTime);
		resp.setNinetyPercentRespTime(ninetyPercentRespTime);
		return resp;
	}

}
