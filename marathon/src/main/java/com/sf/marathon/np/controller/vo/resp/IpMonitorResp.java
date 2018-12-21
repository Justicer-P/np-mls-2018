package com.sf.marathon.np.controller.vo.resp;

import java.io.Serializable;
import java.util.List;

public class IpMonitorResp implements Serializable{
	
	private static final long serialVersionUID = -4610080229906909475L;

	// 请求数
	private List<String> ipRequestCount;
	
	// 失败数
	private List<String> ipFailCount;
	
	// x轴坐标
	private List<String> xAxis;
	
	// 实时请求IP
	private String realTimeIp;
	
	// 实时请求时间
	private String realTimeReqTime;
	
	// 实时请求数
	private String realTimeCount;

	public List<String> getIpRequestCount() {
		return ipRequestCount;
	}

	public void setIpRequestCount(List<String> ipRequestCount) {
		this.ipRequestCount = ipRequestCount;
	}

	public List<String> getIpFailCount() {
		return ipFailCount;
	}

	public void setIpFailCount(List<String> ipFailCount) {
		this.ipFailCount = ipFailCount;
	}

	public String getRealTimeIp() {
		return realTimeIp;
	}

	public void setRealTimeIp(String realTimeIp) {
		this.realTimeIp = realTimeIp;
	}

	public String getRealTimeReqTime() {
		return realTimeReqTime;
	}

	public void setRealTimeReqTime(String realTimeReqTime) {
		this.realTimeReqTime = realTimeReqTime;
	}

	public String getRealTimeCount() {
		return realTimeCount;
	}

	public void setRealTimeCount(String realTimeCount) {
		this.realTimeCount = realTimeCount;
	}

	public List<String> getxAxis() {
		return xAxis;
	}

	public void setxAxis(List<String> xAxis) {
		this.xAxis = xAxis;
	}

}
