package com.sf.marathon.np.controller.vo.req;

import java.io.Serializable;

public class UrlMonitorReq implements Serializable{

	private static final long serialVersionUID = -3369036404849706523L;
	
	// 开始时间
	private String beginTime;
	
	// 结束时间
	private String endTime;

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

}
