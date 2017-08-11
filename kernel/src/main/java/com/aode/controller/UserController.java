package com.aode.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aode.dto.User;
import com.aode.service.IUserService;

import com.aode.util.*;
import com.aode.util.sms.IndustrySMS;
import com.aode.util.sms.SmsJsonVo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService userService;

	/**
	 * 用于注册时用户名/手机/邮箱的验证
	 * 
	 * @param request
	 * @param response
	 * @param loginName
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/checklogin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Map<String, String> isExist(HttpServletRequest request, HttpServletResponse response, String checkedParams)
			throws IOException {
		Map<String, String> msg = new HashMap<String, String>();
		User user = UserUtils.getUserByLoginName(userService, checkedParams);// 通过传入参数获取用户信息

		// 用户已存在
		if (user != null) {
			msg.put("stauts", "error");
		} else {
			msg.put("stauts", "success");
		}

		return msg;
	}

	/**
	 * 登陆验证
	 * 
	 * @param request
	 * @param loginName
	 * @param loginPassword
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Map<String, String> login(HttpServletRequest request, HttpServletResponse response, String loginName,
			String loginPassword, String remember) throws IOException {
		
		Map<String, String> msg = new HashMap<String, String>();
		System.out.println("login "+remember);
		
		Subject subject = SecurityUtils.getSubject();	//取得当前登录的用户
		
		if(subject.isRemembered()){
			
		}

		User user = UserUtils.getUserByLoginName(userService, loginName);
		if (user == null) {
			msg.put("msg", "您输入的账户或密码有误！");
			msg.put("stauts", "401");
		} else {

			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), loginPassword);
			try {
				subject.login(token); // 转入realm检验
				request.getSession().setAttribute("user", user);
				msg.put("url", "index.html");
				msg.put("msg", "登陆成功");
				msg.put("stauts", "200");
			} catch (Exception e) {
				// 校验错误
				// e.printStackTrace();
				msg.put("msg", "您输入的账户或密码有误！");
				msg.put("stauts", "401");
			}
		}

		return msg;
	}

	/**
	 * 退出登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		return "redirect:/index.html";
	}

	/**
	 * 注册验证处理
	 * @param request
	 * @param user
	 * @param verificationCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces= "application/json;charset=UTF-8")
	public Map<String,String> register(HttpServletRequest request,User user,String verificationCode){
		Map<String,String> msg = new HashMap<String,String>();
		HttpSession session = request.getSession();
		String verificationCodeInSession = (String) session.getAttribute("verificationCode");	//取得session中的验证码
		Long verificationSendTimeInSession = (Long) session.getAttribute("verificationSendTime");	//取得session中的验证码发送时间
		if(verificationCodeInSession == null || verificationSendTimeInSession == null || verificationCode == null || user == null){
			msg.put("msg", "异常注册！");
			msg.put("stauts", "error");
		}else{
			if(verificationCode.equals(verificationCodeInSession)){		//验证通过
				if(new Date().getTime() - verificationSendTimeInSession >= 600000){	//超过10分钟验证码失效
					msg.put("msg", "手机验证码已失效，请重新获取验证码！");
					msg.put("stauts", "error");
				}else{
					//注册处理
					//数据检验
					String[] items = {"username","email","phone","password"};
					try {
						Map<String,Boolean> result = UserUtils.checkUser(user, Arrays.asList(items));
						if(result == null || result.isEmpty()){
							msg.put("msg", "异常注册！");
							msg.put("stauts", "error");
						}else{		//检验通过，进行注册
							Set<String> key = result.keySet();
							Iterator<String> iterator = key.iterator();
							boolean continueRegister = true;
							while(iterator.hasNext()){
								String checkeditem = iterator.next();
								Boolean resultBoolean = result.get(checkeditem);
								if(!resultBoolean){
									msg.put("msg", checkeditem+"错误，请重试");
									msg.put("stauts", "error");
									continueRegister = false;
									break;
								}
							}
							if(continueRegister){
								String isRegisterResult = UserUtils.checkIsRegister(userService, user);
								if(isRegisterResult == null){
									user.setCreattime(new Date());
									Integer userId = userService.creatUser(user);
									if(userId != null){
										user.setUserId(userId);
										msg.put("msg", "注册成功，请牢记您的帐号和密码！");
										msg.put("stauts", "success");
									}
									
								}else{
									msg.put("msg", isRegisterResult);
									msg.put("stauts", "error");
								}
								
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
						msg.put("msg", "注册时发生错误，请重新注册！");
						msg.put("stauts", "error");
					} 
				}
			}else{
				msg.put("msg", "手机验证码错误，请重新输入！");
				msg.put("stauts", "error");
			}
		}
		
		return null;
		
	}
	
	/**
	 * 获取手机验证码
	 * @param request
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/sendVerificationCode", method=RequestMethod.POST, produces="application/json;charset=UTF-8" )
	public Map<String,String> sendVerificationCode(HttpServletRequest request,String mobile){
		System.out.println(mobile);
		Map<String,String> msg = new HashMap<String,String>();
		HttpSession session = request.getSession();
		String verificationCode = (String) session.getAttribute("verificationCode");	//取得session中的验证码
		Long verificationSendTime = (Long) session.getAttribute("verificationSendTime");	//取得session中的验证码发送时间
		
		if(verificationCode != null ){
			Long currentTime = new Date().getTime();	
			if(verificationSendTime != null){
				//System.out.println(currentTime - verificationSendTime);
				if(currentTime - verificationSendTime <= 60000){
					msg.put("msg", "不能在短时间内连续发送验证码！");
					msg.put("stauts", "error");
					return msg;
				}
				
			}else{
				msg.put("msg", "异常发送验证码！");
				msg.put("stauts", "error");
				return msg;
			}
		}
		if(RegexUtils.checkPhone(mobile)){
			verificationCode = Integer.toString( ((int)(Math.random()*9000)+1000) );	//取得4位验证码
			verificationSendTime = new Date().getTime();
			String smsContent = "【KB社区】感谢注册KernelBase，您验证码为"+verificationCode+"，请在10分钟内正确输入，如非本人操作，请忽略此短信。";
			//发送验证码
			try {
				SmsJsonVo sms = IndustrySMS.execute(mobile, smsContent);
				if(sms != null && sms.getRespCode().equals("00000")){	//发送成功
					msg.put("msg", "验证码已发送到您的手机，请注意查收！");
					msg.put("stauts", "success");
					session.setAttribute("verificationCode", verificationCode);
					session.setAttribute("verificationSendTime", verificationSendTime);
				}else{
					msg.put("msg", "验证码发送失败，请确认手机无误后重新发送！");
					msg.put("stauts", "error");
				}
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msg.put("msg", "发送验证码时发生错误，请重试！");
				msg.put("stauts","error");
			} 
			
		}else{
			msg.put("msg", "请检查手机号正确后重新获取验证码！");
			msg.put("stauts","error");
		}
		return msg;
		
	}

	/**
	 * 判断用户是否已登录
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/isLogined", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public boolean isLogined(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("user");
		return user != null;
	}
	
	/**
	 * 获取登陆账户名称
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUsername", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Map<String,String> getUsername(HttpServletRequest request){
		Map<String,String> msg = new HashMap<String, String>();
		if(!isLogined(request)){
			msg.put("stauts", "error");
		}else{
			User user = (User) request.getSession().getAttribute("user");
			msg.put("username", user.getUsername());
			msg.put("userId", user.getUserId().toString());
			msg.put("stauts", "success");
		}
		return msg;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getUserinfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public User getUserinfo(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("user");
		if(user != null){
			
			return userService.getUserinfo(user.getUserId());
			
		}else{
			return null;
		}
		
	}
}