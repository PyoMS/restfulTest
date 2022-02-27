package com.pyo.restfulwebservice.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
	
//	@Autowired
	private UserDaoService service;
	
	public AdminUserController(UserDaoService service) {this.service = service;}
	
	@GetMapping("/users")
	public MappingJacksonValue retrieveAllUsers(){
		List<User> users = service.findAll();
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.
				filterOutAllExcept("id","name","join_date","password");
		
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",filter);
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
		mappingJacksonValue.setFilters(filterProvider);
		
		return mappingJacksonValue;
	}
	
	//GET /adimin/users/1 -> /adimin/v1/users/1
//	@GetMapping("/v1/users/{id}") // versioning - URI 방식 
//	@GetMapping(value="/users/{id}/", params = "version=1") // versioning - parameter 방식
//	@GetMapping(value="/users/{id}", headers = "X-API-VERSION=1") // versioning - header 방식
	@GetMapping(value="/users/{id}", produces = "application/vnd.company.appv1+json") // versioning - MIME 방식
	public MappingJacksonValue retrieveUsersV1(@PathVariable int id) throws UserNotFoundException{
		User user = service.findOne(id);
		if(user==null) {
			throw new UserNotFoundException(String.format("ID[%s] not found, ", id));
		}
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.
				filterOutAllExcept("id","name","join_date","ssn");
		
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",filter);
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
		mappingJacksonValue.setFilters(filterProvider);
		
		return mappingJacksonValue;
	}
	
//	@GetMapping("/v2/users/{id}")
//	@GetMapping(value="/users/{id}/", params = "version=2")
//	@GetMapping(value="/users/{id}", headers = "X-API-VERSION=2")
	@GetMapping(value="/users/{id}", produces = "application/vnd.company.appv2+json")
	public MappingJacksonValue retrieveUsersV2(@PathVariable int id) throws UserNotFoundException{
		User user = service.findOne(id);
		if(user==null) {
			throw new UserNotFoundException(String.format("ID[%s] not found, ", id));
		}
		//User -> User2 // version 관리 
		UserV2 userV2 = new UserV2();
		BeanUtils.copyProperties(user, userV2); //****
		userV2.setGrade("VIP");
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.
				filterOutAllExcept("id","name","join_date", "grade");
		
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2",filter);
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
		mappingJacksonValue.setFilters(filterProvider);
		
		return mappingJacksonValue;
	}
	
	
}
