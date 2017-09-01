package com.aode.service;


import java.util.Set;

import com.aode.dto.User;
import com.aode.dto.Userinfo;    
    
public interface IUserService {    
    /**
     * 创建用户
     * @param user
     * @return
     */
    public Integer creatUser(User user);    
    
    /**
     * 通过userId更新用户或用户信息
     * @param user
     * @return
     */
    public Integer updateUserByUserId(User user);
    
    /**
     * 通过username获取用户
     * @param username
     * @return
     */
    public User getByUsername(String username);
    
	public User getByEmail(String email);
	
	public User getByPhone(String phone);
    
	/**
	 * 删除用户
	 * @param userId
	 * @return
	 */
    public Integer deleteByUserId(Integer userId);
    
    /**
     * 取得用户角色
     * @param username
     * @return
     */
    public Set<String> getRoles(String username);
    
    /**
     * 取得用户权限
     * @param username
     * @return
     */
    public Set<String> getPermissions(String username);
    
    /**
     * 修改密码
     * @param username
     * @param newPassword
     * @return
     * @throws Exception
     */
    public Integer changePassword (String username, String newPassword) throws Exception;
    
    /**
     * 取得用户信息
     * @param userId
     * @return
     */
    public User getUserinfo(Integer userId);
}    
