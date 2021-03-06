package com.pyo.restfulwebservice.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserDaoService {
	private static List<User> users = new ArrayList<>();
	private static int userCount = 3;
	static {
		users.add(new User(1,"kenneth", new Date(), "pass1", "701010-111111"));
		users.add(new User(2,"Alice", new Date(), "pass2", "801010-111111"));
		users.add(new User(3,"Elena", new Date(), "pass3", "901010-111111"));
	}
	
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user) {
		if(user.getId() == null) {
			user.setId(++userCount);
		}
		users.add(user);
		return user;
	}
	
	public User findOne(int id) {
//		for (int i = 0; i < users.size(); i++) {
//			if(users.get(i).getId() == id) {
//				return user;
//			}
//		}
		for (User user : users) {
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	public User deletebyId(int id) {
		Iterator<User> iterator  = users.iterator();
		while(iterator.hasNext()) {
			User user = iterator.next();
			
			if(user.getId()==id) {
				iterator.remove();
				return user;
			}
		}
		return null;
	}
	
	public User modifiedNamebyId(int id, String name) {
		Iterator<User> iterator = users.iterator();
		while(iterator.hasNext()) {
			User user = iterator.next();
			if(user.getId()==id) {
				user.setName(name);
				return user;
			}
		}
		return null;
	}
}
