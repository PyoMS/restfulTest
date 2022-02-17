package com.pyo.restfulwebservice.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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
	
	//GET /users/1 or /users/10
	@GetMapping("/users/{id}")
	public MappingJacksonValue retrieveUsers(@PathVariable int id) throws UserNotFoundException{
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
	
	
}
