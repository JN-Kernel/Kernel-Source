package com.aode.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aode.dto.Permission;
import com.aode.dto.User;
import com.aode.dto.UserRole;
import com.aode.dto.Userinfo;

public interface UserMapper {

	public User getByUsername(String username);
	
	public User getByEmail(String email);
	
	public User getByPhone(String phone);

	public User getById(Integer id);
	
	public Integer creatUser(User user);

	public Integer deleteByUserId(Integer userId);

	public Integer updateUser(User user);

	public Integer changePassword(String username, String newPassword);

	// 添加用户-角色关系
	public Integer correlationRoles(UserRole userRole); 

	// 移除用户-角色关系
	public Integer uncorrelationRoles(UserRole userRole);
	
	// 根据用户名查找其角色
	public Set<String> getRoles(String username);

	// 根据用户名查找其权限
	public Set<String> getPermissions(String username);
	
	public Userinfo getUserinfo(Integer userId);

}