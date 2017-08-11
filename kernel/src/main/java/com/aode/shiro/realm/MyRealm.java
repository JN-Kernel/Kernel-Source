package com.aode.shiro.realm;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.aode.dto.User;
import com.aode.service.IUserService;

public class MyRealm extends AuthorizingRealm {

	@Resource
	private IUserService userService;

	// 为当前登陆成功的用户授予权限和角色，已经登陆成功了
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal(); // 获取用户名
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		System.out.println("==> Authorization \n   ==> " + username);

		authorizationInfo.setRoles(userService.getRoles(username)); // 设置角色
		authorizationInfo.setStringPermissions(userService.getPermissions(username)); // 设置权限
		
		return authorizationInfo;
	}

	// 验证当前登录的用户，获取认证信息
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal(); // 获取用户名
		System.out.println("==> Authentication\n  ==> " + username + "\n  ==>" + getName());

		User user = userService.getByUsername(username); // 仅仅是根据用户名查出的用户信息，不涉及到密码
		if (user != null) {
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),getName());
			return authcInfo;
		}else{
			return null;
		}
	}
}
