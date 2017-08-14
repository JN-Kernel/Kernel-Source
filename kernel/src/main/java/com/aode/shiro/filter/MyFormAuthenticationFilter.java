 package com.aode.shiro.filter;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aode.dao.UserMapper;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter{

	 private static final Logger log = LoggerFactory.getLogger(FormAuthenticationFilter.class);

	    @Autowired
	    UserMapper userMapper;

	    /**
	     * 表示当访问拒绝时
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    @Override
	    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
	    	System.out.println("onAccessDenied");
	        if(this.isLoginRequest(request, response)) {
	            if(this.isLoginSubmission(request, response)) {
	                if(log.isTraceEnabled()) {
	                    log.trace("Login submission detected.  Attempting to execute login.");
	                }

	                return this.executeLogin(request, response);
	            } else {
	                if(log.isTraceEnabled()) {
	                    log.trace("Login page view.");
	                }

	                return true;
	            }
	        } else {
	            if(log.isTraceEnabled()) {
	                log.trace("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [" + this.getLoginUrl() + "]");
	            }

	            this.saveRequestAndRedirectToLogin(request, response);
	            return false;
	        }
	    }

	    /**
	     * 当登录成功
	     * @param token
	     * @param subject
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    @Override
	    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
	    	System.out.println("onLoginSuccess");
	        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

	        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest
	                .getHeader("X-Requested-With"))) {// 不是ajax请求
	            issueSuccessRedirect(request, response);
	        } else {
	            httpServletResponse.setCharacterEncoding("UTF-8");
	            PrintWriter out = httpServletResponse.getWriter();
	            out.println("{\"success\":true,\"message\":\"登录成功\"}");
	            out.flush();
	            out.close();
	        }
	        return false;
	    }

	    /**
	     * 当登录失败
	     * @param token
	     * @param e
	     * @param request
	     * @param response
	     * @return
	     */
	    @Override
	    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
	       System.out.println("onLoginFailure");
	    	if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request)
	                .getHeader("X-Requested-With"))) {// 不是ajax请求
	            setFailureAttribute(request, e);
	            return true;
	        }
	        try {
	            response.setCharacterEncoding("UTF-8");
	            PrintWriter out = response.getWriter();
	            String message = e.getClass().getSimpleName();
	            if ("IncorrectCredentialsException".equals(message)) {
	                out.println("{\"success\":false,\"message\":\"密码错误\"}");
	            } else if ("UnknownAccountException".equals(message)) {
	                out.println("{\"success\":false,\"message\":\"账号不存在\"}");
	            } else if ("LockedAccountException".equals(message)) {
	                out.println("{\"success\":false,\"message\":\"账号被锁定\"}");
	            } else {
	                out.println("{\"success\":false,\"message\":\"未知错误\"}");
	            }
	            out.flush();
	            out.close();
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }
	        return false;
	    }
	}
