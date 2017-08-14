package com.aode.controller;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.aode.dto.User;
@RunWith(value=SpringJUnit4ClassRunner.class) 
@WebAppConfiguration 
@ContextConfiguration(value="classpath:applicationContext.xml")
public class UserControllerTest {

	@Resource
	HttpServletRequest  request;
	
	@Test
	public void testRegister() {
		User user = new User();
		user.setUsername("user1");
		user.setEmail("u@email.com");
		user.setPassword("password1");
		user.setPhone("13800138003");
		
		UserController userController = new UserController();
		Map<String, String> result= userController.register(request, user, "1234");
		Iterator<String> test = result.keySet().iterator();
		while(test.hasNext()){
			String str = test.next();
			System.out.println("key: "+str+" value: "+result.get(str));
		}
	}

}
