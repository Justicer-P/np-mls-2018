package com.sf.marathon.np.controller.vo.resp;

import java.io.Serializable;
import java.util.List;

public class AllUrlsResp implements Serializable{

	private static final long serialVersionUID = -3296342262533843142L;
	
	// 所有url
	private List<String> urls;

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}
	
}
