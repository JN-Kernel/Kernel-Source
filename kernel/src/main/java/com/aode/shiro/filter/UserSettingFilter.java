package com.aode.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.aode.dto.User;
import com.aode.service.IUserService;

public class UserSettingFilter extends AccessControlFilter{

	   
    @Autowired  
    private IUserService userService;  
	
    /**
     * remberme中自动添加用户信息
     */
    @Override  
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {  
    	Subject subject = getSubject(request, response);  
        if (subject == null) {  
            return false;  
        }  
        HttpSession session = ((HttpServletRequest)request).getSession();  
        User current_user = (User) session.getAttribute("user");  //取出user信息
        //判断session是否失效，若失效刷新之  
        if(current_user == null){  
            String username = (String) subject.getPrincipal();  
            User user = userService.getByUsername(username);  
            session.setAttribute("user", user);
            session.setAttribute("isRemberedLogin", true);
        }  
        return true;  
    }  
    
    
	/**
	 * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return true;
	}

	/**
	 * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return true;
	}
	
	
}
