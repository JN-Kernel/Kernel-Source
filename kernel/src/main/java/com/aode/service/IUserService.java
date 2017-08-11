package com.aode.service;


import java.util.Set;

import com.aode.dto.User;    
    
public interface IUserService {    
    
    public Integer creatUser(User user);    
    
    public void updateUser(User user);
    
    public User getByUsername(String username);
    
	public User getByEmail(String email);
	
	public User getByPhone(String phone);
    
    public void deleteByUserId(Integer userId);
    
    public Set<String> getRoles(String username);
    
    public Set<String> getPermissions(String username);
    
    public void changePassword (String username, String newPassword) throws Exception;
    
    public User getUserinfo(Integer userId);
}    
