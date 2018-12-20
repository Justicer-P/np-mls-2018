package com.sf.marathon.np.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.marathon.np.controller.vo.req.ExampleReq;
import com.sf.marathon.np.controller.vo.resp.ExampleResp;
import com.sf.marathon.np.controller.vo.resp.RestResponse;
import com.sf.marathon.np.domain.ExampleDomain;
import com.sf.marathon.np.service.IExampleService;
import com.sf.marathon.np.util.JacksonUtil;

@RequestMapping("/example")
@Controller
public class ExampleController extends BaseController {

	@Autowired
	private IExampleService exampleService;

	@GetMapping("/hello")
	@ResponseBody
	public RestResponse<String> example1() {
		return handle(r -> r.setResult(exampleService.sayHello()));
	}

	@GetMapping("/getExampleResp")
	@ResponseBody
	public RestResponse<ExampleResp> example2() {
		return handle(r -> r.setResult(exampleService.getExampleResp()));
	}

	@PostMapping("/getExampleReq")
	@ResponseBody
	public RestResponse<ExampleDomain> example3(@RequestBody ExampleReq req) {
		return handle(r -> r.setResult(exampleService.getExampleReq(req)));
	}

	public static void main(String[] args) {
		ExampleReq e = new ExampleReq();
		e.setGroupId(112L);
		e.setUserName("你大爷");
		e.setNumPerDay(34353);
		e.setUserAddress("你大爷家");
		e.setUserCellphone("12345678901");
		e.setWeightPerDay(9.99);
		System.out.println(JacksonUtil.beanToJson(e));
	}

}
