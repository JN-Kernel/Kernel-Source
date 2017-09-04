package com.aode.service.impl;

import static org.junit.Assert.*;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.aode.dto.User;
import com.aode.service.IUserService;
@RunWith(value=SpringJUnit4ClassRunner.class) 
@WebAppConfiguration 
@ContextConfiguration(value="classpath:applicationContext.xml")
public class UserServiceImplTest {
	private User user = new User();
	public UserServiceImplTest() {
		user.setUserId(83);
		user.setUsername("test user");
		user.setEmail("test@email");
		user.setPhone("12345678901");
		user.setCreattime(new Date());
		user.setPassword("123456");
	}
	@Resource
	IUserService userService;
	
	@Test
	public void testGetByUsername() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRoles() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPermissions() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreatUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUserByUserId() {
		fail("Not yet implemented");
//		Integer s = userService.updateUserByUserId(user);
//		System.out.println(s);
	}

	@Test
	public void testDeleteByUserId() {
		fail("Not yet implemented");
		Integer s = userService.deleteByUserId(4);
		System.out.println(s);
	}

	@Test
	public void testChangePassword() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetByEmail() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetByPhone() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserinfo() {
		fail("Not yet implemented");
	}

}
