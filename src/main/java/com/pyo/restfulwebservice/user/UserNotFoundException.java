package com.pyo.restfulwebservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 2xx -> ok
// 4xx -> client 측 에러
// 5xx -> server 측 에러
@ResponseStatus(HttpStatus.NOT_FOUND) // 해당 Exception이 어떤 Status를 던져주는 건지 어노테이션으로 설정할 수 있음.
public class UserNotFoundException extends Exception {

	public UserNotFoundException(String message) {
		super(message);
	}
	
}
