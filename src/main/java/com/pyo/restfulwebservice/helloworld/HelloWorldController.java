package com.pyo.restfulwebservice.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	//GET
	// /hello-world (endpoint)
	// @RequestMapping(mehtod=RequestMethod.GET, path = "")
	
	@GetMapping(path = "/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	//TODO Bean return type 공부해보기.
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		HelloWorldBean helloWorldBean = new HelloWorldBean();
		helloWorldBean.setMeessage("test");
//		return new HelloWorldBean("Hello World");
		return helloWorldBean;
		
	}
	
	@GetMapping(path = "/hello-world-bean/path-variable/{name}")
	public HelloWorldBean helloWorldBean(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name));
		
	}
}
