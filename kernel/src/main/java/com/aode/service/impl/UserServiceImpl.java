package com.aode.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;    
    
import org.springframework.stereotype.Service;

import com.aode.dao.UserMapper;
import com.aode.dto.User;    
import com.aode.service.IUserService;    
    
@Service("userService")    
public class UserServiceImpl implements IUserService {    
    @Resource    
    private UserMapper userMapper;    
   
    
	@Override
    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

	@Override
    public Set<String> getRoles(String username) {
        return userMapper.getRoles(username);
    }

	@Override
    public Set<String> getPermissions(String username) {
        return userMapper.getPermissions(username);
    }

	@Override
	public Integer creatUser(User user) {
		return userMapper.creatUser(user);
	}

	@Override
	public void updateUser(User user) {
		userMapper.updateUser(user);
	}

	@Override
	public void deleteByUserId(Integer userId) {
		userMapper.deleteByUserId(userId);
	}

	@Override
	public void changePassword(String username, String newPassword) throws Exception {
		User user = null;
		user = this.getByUsername(username);
		if(user != null){
			user.setPassword(newPassword);
		}else{
			throw new Exception("用户不存在");
		}
	}

	@Override
	public User getByEmail(String email) {
		 return userMapper.getByEmail(email);
	}

	@Override
	public User getByPhone(String phone) {
		return userMapper.getByPhone(phone);
	}

	@Override
	public User getUserinfo(Integer userId) {
		return userMapper.getUserinfo(userId);
	}



    
}    
