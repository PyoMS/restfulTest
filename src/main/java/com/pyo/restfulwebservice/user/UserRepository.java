package com.pyo.restfulwebservice.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository	//Db에 관련된 형태의 Bean
public interface UserRepository extends JpaRepository<User, Integer>{ //사용 class와 해당 class의 기준값
	
}
