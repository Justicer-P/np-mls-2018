package com.sf.marathon.np.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sf.marathon.np.controller.vo.resp.RestResponse;

public class BaseController {
	protected final Log log = LogFactory.getLog(this.getClass());

	private static final Log innerLog = LogFactory.getLog(BaseController.class);

	protected static <T> RestResponse<T> handle(Action<T> action) {
		RestResponse<T> restResponse = new RestResponse<T>();
		try {
			restResponse.setSuccess(true);
			action.onHandle(restResponse);
		} catch (Exception e) {
			innerLog.warn("warn", e);
			restResponse.setSuccess(false);
			restResponse.setMsg(e.toString());
		}
		return restResponse;
	}

	protected static interface Action<E> {
		void onHandle(RestResponse<E> restResponse);
	}

}
