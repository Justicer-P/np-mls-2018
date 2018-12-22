package com.sf.marathon.np.controller.vo.req;

import java.io.Serializable;

public class IpMonitorReq implements Serializable{

	private static final long serialVersionUID = 2991826418912813806L;
	
	// ip
	private String ip;
	
	// 开始时间
	private String beginTime;
	
	// 结束时间
	private String endTime;
	
	// 源IP
	private String sourceIp;
	
	// 目标IP
	private String destIp;
	
	// 实时请求IP
	private String realTimeIp;
	
	// 实时请求时间
	private String realTimeReqTime;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRealTimeReqTime() {
		return realTimeReqTime;
	}

	public void setRealTimeReqTime(String realTimeReqTime) {
		this.realTimeReqTime = realTimeReqTime;
	}

	public String getRealTimeIp() {
		return realTimeIp;
	}

	public void setRealTimeIp(String realTimeIp) {
		this.realTimeIp = realTimeIp;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSourceIp() {
		return sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public String getDestIp() {
		return destIp;
	}

	public void setDestIp(String destIp) {
		this.destIp = destIp;
	}
	
}
