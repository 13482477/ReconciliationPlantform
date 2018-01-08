package com.yonyou.reconciliation.user.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yonyou.reconciliation.Application;
import com.yonyou.reconciliation.user.entity.User;
import com.yonyou.reconciliation.user.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest{
	
	@Autowired
	private UserService userService;
	
	@Test
	public void createUserTest() {
		User user = new User();
		user.setId((long) 1);
		user.setUsername("lizhiqiang");
		user.setPassword("password2");
		
		this.userService.save(user);
		
//		this.userService.createUser(user);
//		
//		User user2 = this.userService.loadUserByUsername("lizhiqiang");
		
		
		System.out.println(user.toString());
	}

}
