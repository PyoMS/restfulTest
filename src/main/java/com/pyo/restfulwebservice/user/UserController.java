package com.pyo.restfulwebservice.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {
	
	@Autowired
	private UserDaoService service;
	
	public UserController(UserDaoService service) {
		this.service = service;
	}
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	
	//GET /users/1 or /users/10
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveAllUsers(@PathVariable int id) throws UserNotFoundException{
		User user = service.findOne(id);
		if(user==null) {
			throw new UserNotFoundException(String.format("ID[%s] not found, ", id));
		}
		
		// HATEOS
		EntityModel<User> model = EntityModel.of(user);
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		model.add(linkTo.withRel("all-users"));
		
		return model;
		
//		return user;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user){
		System.out.println("@createUser - " + user.toString());
		User savedUser = service.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(savedUser.getId())
		.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) throws UserNotFoundException {
		User user = service.deletebyId(id);
		
		if(user==null) {
			throw new UserNotFoundException(String.format("ID[%s] not found, ", id));
		}
	}
	
	@PutMapping("/users/{id}/{name}")
	public void modifiedUser(@PathVariable int id, @PathVariable String name) throws UserNotFoundException {
		User user = service.modifiedNamebyId(id,name);
		
		if(user==null) {
			throw new UserNotFoundException(String.format("ID[%s] not found, ", id));
		}
	}
	
}
