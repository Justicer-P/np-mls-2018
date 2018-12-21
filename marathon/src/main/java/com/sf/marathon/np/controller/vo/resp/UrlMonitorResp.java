package com.sf.marathon.np.controller.vo.resp;

import java.io.Serializable;
import java.util.List;

public class UrlMonitorResp implements Serializable{

	private static final long serialVersionUID = -3659004224350009870L;
	
	// Url
	private String url;
	
	// 请求数
	private List<String> urlRequestCount;
	
	// 失败数
	private List<String> urlFailCount;
	
	// x轴坐标
	private List<String> xAxis;
	
	// 最长响应时间
	private String longestRespTime;
	
	// 最短响应时间
	private String shortestRespTime;
	
	// 平均响应时间
	private String avgRespTime;
	
	// 90%响应时间
	private String NinetyPercentRespTime;

	public List<String> getUrlRequestCount() {
		return urlRequestCount;
	}

	public void setUrlRequestCount(List<String> urlRequestCount) {
		this.urlRequestCount = urlRequestCount;
	}

	public List<String> getUrlFailCount() {
		return urlFailCount;
	}

	public void setUrlFailCount(List<String> urlFailCount) {
		this.urlFailCount = urlFailCount;
	}

	public List<String> getxAxis() {
		return xAxis;
	}

	public void setxAxis(List<String> xAxis) {
		this.xAxis = xAxis;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLongestRespTime() {
		return longestRespTime;
	}

	public void setLongestRespTime(String longestRespTime) {
		this.longestRespTime = longestRespTime;
	}

	public String getShortestRespTime() {
		return shortestRespTime;
	}

	public void setShortestRespTime(String shortestRespTime) {
		this.shortestRespTime = shortestRespTime;
	}

	public String getAvgRespTime() {
		return avgRespTime;
	}

	public void setAvgRespTime(String avgRespTime) {
		this.avgRespTime = avgRespTime;
	}

	public String getNinetyPercentRespTime() {
		return NinetyPercentRespTime;
	}

	public void setNinetyPercentRespTime(String ninetyPercentRespTime) {
		NinetyPercentRespTime = ninetyPercentRespTime;
	}
	
}
