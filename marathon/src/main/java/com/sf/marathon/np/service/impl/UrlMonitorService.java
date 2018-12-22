package com.sf.marathon.np.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sf.marathon.np.common.UrlMonitorType;
import com.sf.marathon.np.controller.vo.req.UrlMonitorReq;
import com.sf.marathon.np.controller.vo.resp.UrlMonitorResp;
import com.sf.marathon.np.index.api.API;
import com.sf.marathon.np.service.IUrlMonitorService;
import com.sf.marathon.np.util.TimeUtil;

@Service
public class UrlMonitorService implements IUrlMonitorService {

	@Autowired
	private API api;
	
	@Override
	public UrlMonitorResp urlMonitor(UrlMonitorReq req, String type, boolean isFirst) {
		UrlMonitorResp resp = new UrlMonitorResp();
		Map<String, Number[]> result;
		List<String> xAxis = new ArrayList<>();
		switch (type) {
		case UrlMonitorType.URL_MONITOR:
			List<String> urlRequestCount = new ArrayList<>();
			List<String> urlFailCount = new ArrayList<>();
			if (isFirst) {
				result = api.sumRequestGroupByURL(req.getBeginTime(), req.getEndTime());
				Map<String, Number[]> sortedMap = new TreeMap<>((m1, m2) -> {
					return m1.substring(0, m1.indexOf("~")).compareTo(m2.substring(0, m2.indexOf("~")));
				});
				sortedMap.putAll(result);
				Set<String> urls = new HashSet<>();
				final boolean[] flag = new boolean[] { true };
				final String[] url = new String[] { "" };
				sortedMap.forEach((k, v) -> {
					int index = k.indexOf("~");
					String tmpUrl = k.substring(index + 1);
					if (flag[0]) { // 第一条url
						url[0] = tmpUrl;
						setUrlMonitor(xAxis, urlRequestCount, urlFailCount, k, v, index);
						urls.add(tmpUrl);
						flag[0] = false;
					} else if (url[0].equals(tmpUrl)) { // 与第一条url相等
						setUrlMonitor(xAxis, urlRequestCount, urlFailCount, k, v, index);
					} else {
						urls.add(tmpUrl);
					}
				});
				resp.setUrl(url[0]);
				resp.setUrls(new ArrayList<>(urls));
			} else {
				result = api.sumRequestGroupByURL(req.getBeginTime(), req.getEndTime(), req.getUrl());
				Map<String, Number[]> sortedMap = new TreeMap<>((m1, m2) -> {
					return m1.substring(0, m1.indexOf("~")).compareTo(m2.substring(0, m2.indexOf("~")));
				});
				sortedMap.putAll(result);
				sortedMap.forEach((k, v) -> {
					int index = k.indexOf("~");
					setUrlMonitor(xAxis, urlRequestCount, urlFailCount, k, v, index);
				});
				resp.setUrl(req.getUrl());
			}
			resp.setUrlRequestCount(urlRequestCount);
			resp.setUrlFailCount(urlFailCount);
			break;
		case UrlMonitorType.URL_SUM_MONITOR:
			List<String> longestRespTime = new ArrayList<>();
			List<String> shortestRespTime = new ArrayList<>();
			List<String> avgRespTime = new ArrayList<>();
			List<String> ninetyPercentRespTime = new ArrayList<>();
			if (isFirst) {
				result = api.mulTiAggregation(req.getBeginTime(), req.getEndTime());
				Map<String, Number[]> sortedMap = new TreeMap<>((m1, m2) -> {
					return m1.substring(0, m1.indexOf("~")).compareTo(m2.substring(0, m2.indexOf("~")));
				});
				sortedMap.putAll(result);
				Set<String> urls = new HashSet<>();
				final boolean[] flag = new boolean[] { true };
				final String[] url = new String[] { "" };
				sortedMap.forEach((k, v) -> {
					int index = k.indexOf("~");
					String tmpUrl = k.substring(index + 1);
					if (flag[0]) { // 第一条url
						url[0] = tmpUrl;
						setMonitorSummary(xAxis, longestRespTime, shortestRespTime, avgRespTime, ninetyPercentRespTime, k, v,
								index);
						urls.add(tmpUrl);
						flag[0] = false;
					} else if (url[0].equals(tmpUrl)) { // 与第一条url相等
						setMonitorSummary(xAxis, longestRespTime, shortestRespTime, avgRespTime, ninetyPercentRespTime, k, v,
								index);
					} else {
						urls.add(tmpUrl);
					}
				});
				resp.setUrl(url[0]);
				resp.setUrls(new ArrayList<>(urls));
			} else {
				result = api.mulTiAggregation(req.getBeginTime(), req.getEndTime(), req.getUrl());
				Map<String, Number[]> sortedMap = new TreeMap<>((m1, m2) -> {
					return m1.substring(0, m1.indexOf("~")).compareTo(m2.substring(0, m2.indexOf("~")));
				});
				sortedMap.putAll(result);
				sortedMap.forEach((k, v) -> {
					int index = k.indexOf("~");
					setMonitorSummary(xAxis, longestRespTime, shortestRespTime, avgRespTime, ninetyPercentRespTime, k, v,
							index);
				});
				resp.setUrl(req.getUrl());
			}
			resp.setLongestRespTime(longestRespTime);
			resp.setShortestRespTime(shortestRespTime);
			resp.setAvgRespTime(avgRespTime);
			resp.setNinetyPercentRespTime(ninetyPercentRespTime);
			break;
		default:
			throw new RuntimeException("unsupported type string!");
		}
		resp.setxAxis(xAxis);
		return resp;
	}
	
	private void setUrlMonitor(List<String> xAxis, List<String> urlRequestCount, List<String> urlFailCount, String k,
			Number[] v, int index) {
		String time = TimeUtil.formatLong("yyyy-MM-dd HH:mm", Long.valueOf(k.substring(0, index)));
		xAxis.add(time);
		urlRequestCount.add(String.valueOf(v[0]));
		urlFailCount.add(String.valueOf(v[1]));
	}
	
	private void setMonitorSummary(List<String> xAxis, List<String> longestRespTime, List<String> shortestRespTime,
			List<String> avgRespTime, List<String> ninetyPercentRespTime, String k, Number[] v, int index) {
		setUrlMonitor(xAxis, longestRespTime, shortestRespTime, k, v, index);
		avgRespTime.add(String.valueOf(v[1]));
		ninetyPercentRespTime.add(String.valueOf(v[1]));
	}

}
