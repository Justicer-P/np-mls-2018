package com.sf.marathon.np.service;

import com.sf.marathon.np.controller.vo.req.ExampleReq;
import com.sf.marathon.np.controller.vo.resp.ExampleResp;
import com.sf.marathon.np.domain.ExampleDomain;

public interface IExampleService {
	
	public String sayHello();
	
	public ExampleResp getExampleResp();
	
	public ExampleDomain getExampleReq(ExampleReq req);
	
}
