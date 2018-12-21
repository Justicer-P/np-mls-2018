package com.sf.marathon.np.controller.vo.resp;

import java.io.Serializable;

public class RestResponse<T> implements Serializable {
	private static final long serialVersionUID = 3456658419924296764L;

	private Boolean success;

	private T result;

	private String msg;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}