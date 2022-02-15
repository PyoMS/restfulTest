package com.pyo.restfulwebservice.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//lombok lib - getter metter 기능 자동 처리해줌.

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldBean {
	private String meessage;
	
	
//	 public HelloWorldBean(String message){ }
	 

}
