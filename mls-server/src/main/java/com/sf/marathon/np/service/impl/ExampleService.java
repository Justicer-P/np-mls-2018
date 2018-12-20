package com.sf.marathon.np.service.impl;

import org.springframework.stereotype.Service;

import com.sf.marathon.np.controller.vo.req.ExampleReq;
import com.sf.marathon.np.controller.vo.resp.ExampleResp;
import com.sf.marathon.np.domain.ExampleDomain;
import com.sf.marathon.np.service.IExampleService;
import com.sf.marathon.np.util.BeanUtil;
import com.sf.marathon.np.util.TimeUtil;

@Service
public class ExampleService implements IExampleService{

	@Override
	public String sayHello() {
		return "Hello World!";
	}

	@Override
	public ExampleResp getExampleResp() {
		ExampleResp e = new ExampleResp();
		e.setCreateTime(TimeUtil.now());
		e.setGroupType("aaaaa");
		e.setId(123123L);
		e.setItemWeight(1.2123);
		e.setMinBagNum(123123);
		return e;
	}

	@Override
	public ExampleDomain getExampleReq(ExampleReq req) {
		return BeanUtil.copyProperties(req, ExampleDomain.class);
	}

}
