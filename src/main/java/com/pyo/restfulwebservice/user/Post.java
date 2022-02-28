package com.pyo.restfulwebservice.user;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	@Id
	@GeneratedValue
	private Integer id;
	
	private String description;
	
	// User : Post = 1: (0~N)
	@ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 방식
	@JsonIgnore //외부에 데이터 노출 방지.
	private User user; // db 생성시에 User 클래스의 id를 갖는다.
	
}
