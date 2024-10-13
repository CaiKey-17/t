package com.example.Rest_API;

import com.example.Rest_API.model.User;
import com.example.Rest_API.service.serviceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RestApiApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	serviceImpl service;

	@Test
	void addUserTest(){
		User u = new User();
		u.setEmail("a@gmail.com");
		u.setName("Luân");
		service.addUser(u);
	}
	@Test
	void listU(){
		for(User user: service.listUser()){
			System.out.println(user);
		}
	}
}
