package com.sf.marathon.np.controller.vo.resp;

import java.io.Serializable;
import java.util.List;

public class AllIpsResp implements Serializable{

	private static final long serialVersionUID = -4820504102208272095L;
	
	// 所有源ip
	private List<String> sourceIps;
	
	// 所有目标ip
	private List<String> destIps;

	public List<String> getSourceIps() {
		return sourceIps;
	}

	public void setSourceIps(List<String> sourceIps) {
		this.sourceIps = sourceIps;
	}

	public List<String> getDestIps() {
		return destIps;
	}

	public void setDestIps(List<String> destIps) {
		this.destIps = destIps;
	}
	
}
