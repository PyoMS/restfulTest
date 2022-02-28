package com.pyo.restfulwebservice.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(id); //findById리턴값 Optional 클래스 사용.
		
		if(!user.isPresent()) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		// HATEOS - 전체 data 조회에 대한 주소값을 반환해 준다.
		EntityModel<User> model = EntityModel.of(user.get());
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		model.add(linkTo.withRel("all-users"));
		
		return model;
//		return user.get();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user){
		User savedUser = userRepository.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	//개별 사용자의 게시글 조회
	@GetMapping("/users/{id}/posts")
	public List<Post> retrieveAllPostsByUser(@PathVariable int id) throws UserNotFoundException{
		Optional<User> user = userRepository.findById(id); //findById리턴값 Optional 클래스 사용.
		if(!user.isPresent()) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		return user.get().getPosts(); //getPosts() -> lombok에서 getter 생성해줌.
	}
	
	@PostMapping("/users/{id}/posts")
	public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) throws UserNotFoundException{
		//1. 사용자 정보 조회
		Optional<User> user = userRepository.findById(id); //findById리턴값 Optional 클래스 사용.
		if(!user.isPresent()) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		post.setUser(user.get()); // 해당 post 객체에 set User
		
		//2. 해당 사용자의 post list에 save
		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
}
