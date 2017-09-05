package com.aode.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aode.dto.Topic;
import com.aode.dto.TopicContent;
import com.aode.dto.TopicReply;
import com.aode.dto.User;
import com.aode.dto.Userinfo;
import com.aode.service.ITopicService;
import com.aode.service.IUserService;

import com.aode.util.*;
import com.aode.util.sms.IndustrySMS;
import com.aode.util.sms.SmsJsonVo;
import com.baidu.ueditor.ActionEnter;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService userService;

	@Resource
	private ITopicService topicService;

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
		System.out.println("login " + remember);

		Subject subject = SecurityUtils.getSubject(); // 取得当前登录的用户

		if (subject.isRemembered()) {
			System.out.println("isRemembered");
		} else {
			System.out.println("notRemembered");
		}

		User user = UserUtils.getUserByLoginName(userService, loginName);
		if (user == null) {
			msg.put("msg", "您输入的账户或密码有误！");
			msg.put("stauts", "401");
		} else {

			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), loginPassword);
			if (remember != null) {
				token.setRememberMe(true);
			} else {
				token.setRememberMe(false);
			}
			try {
				subject.login(token); // 转入realm检验
				request.getSession().setAttribute("user", user);
				msg.put("url", "user_info.html");
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

	@ResponseBody
	@RequestMapping(value="/changePassword", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String changePassword(HttpServletRequest request,String newPassword,String oldPassword){
		User user = (User) request.getSession().getAttribute("user");
		String msg = "errorPass";
		if(user == null){
			return "nologin";
		}
		if(newPassword == null || newPassword == "" || oldPassword == null || oldPassword ==""){
			return msg;
		}else{
			if( oldPassword.equals(userService.getByUsername(user.getUsername()).getPassword())){
				try {
					if( userService.changePassword(user.getUsername(), newPassword) >0){
						msg = "success";
					}else{
						msg = "error";						
					}
				} catch (Exception e) {
					return "error";
				}
				
			}
		}
			
		return msg;
		
	}
	
	/**
	 * 注册验证处理
	 * 
	 * @param request
	 * @param user
	 * @param verificationCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Map<String, String> register(HttpServletRequest request, User user, String verificationCode) {
		Map<String, String> msg = new HashMap<String, String>();
		HttpSession session = request.getSession();
		String verificationCodeInSession = (String) session.getAttribute("verificationCode"); // 取得session中的验证码
		Long verificationSendTimeInSession = (Long) session.getAttribute("verificationSendTime"); // 取得session中的验证码发送时间
		if (verificationCodeInSession == null || verificationSendTimeInSession == null || verificationCode == null
				|| user == null) {
			msg.put("msg", "异常注册！");
			msg.put("stauts", "error");
		} else {
			if (verificationCode.equals(verificationCodeInSession)) { // 验证通过
				if (new Date().getTime() - verificationSendTimeInSession >= 600000) { // 超过10分钟验证码失效
					msg.put("msg", "手机验证码已失效，请重新获取验证码！");
					msg.put("stauts", "error");
				} else {
					// 注册处理
					// 数据检验
					String[] items = { "username", "email", "phone", "password" };
					System.out.println(Arrays.asList(items));
					try {
						System.out.println("checkUser before");
						Map<String, Boolean> result = UserUtils.checkUser(user, Arrays.asList(items));
						System.out.println("checkUser after");
						if (result == null || result.isEmpty()) {
							msg.put("msg", "异常注册！");
							msg.put("stauts", "error");
						} else { // 检验通过，进行注册
							Set<String> key = result.keySet();
							Iterator<String> iterator = key.iterator();
							boolean continueRegister = true;
							while (iterator.hasNext()) {
								String checkeditem = iterator.next();
								Boolean resultBoolean = result.get(checkeditem);
								System.out.println("checkeditem: " + checkeditem + "  resultBoolean: " + resultBoolean);
								if (!resultBoolean) {
									msg.put("msg", checkeditem + "错误，请重试");
									msg.put("stauts", "error");
									continueRegister = false;
									break;
								}
							}
							if (continueRegister) {
								String isRegisterResult = UserUtils.checkIsRegister(userService, user);
								if (isRegisterResult == null) {
									user.setCreattime(new Date());
									Integer creatResult = userService.creatUser(user);
									if (creatResult != null) {
										msg.put("msg", "注册成功，请牢记您的帐号和密码！");
										msg.put("stauts", "success");
									}

								} else {
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
			} else {
				msg.put("msg", "手机验证码错误，请重新输入！");
				msg.put("stauts", "error");
			}
		}
		System.out.println(msg.toString());
		return msg;

	}

	/**
	 * 获取手机验证码
	 * 
	 * @param request
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendVerificationCode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Map<String, String> sendVerificationCode(HttpServletRequest request, String mobile) {
		System.out.println(mobile);
		Map<String, String> msg = new HashMap<String, String>();
		HttpSession session = request.getSession();
		String verificationCode = (String) session.getAttribute("verificationCode"); // 取得session中的验证码
		Long verificationSendTime = (Long) session.getAttribute("verificationSendTime"); // 取得session中的验证码发送时间

		if (verificationCode != null) {
			Long currentTime = new Date().getTime();
			if (verificationSendTime != null) {
				// System.out.println(currentTime - verificationSendTime);
				if (currentTime - verificationSendTime <= 60000) {
					msg.put("msg", "不能在短时间内连续发送验证码！");
					msg.put("stauts", "error");
					return msg;
				}

			} else {
				msg.put("msg", "异常发送验证码！");
				msg.put("stauts", "error");
				return msg;
			}
		}
		if (RegexUtils.checkPhone(mobile)) {
			verificationCode = Integer.toString(((int) (Math.random() * 9000) + 1000)); // 取得4位验证码
			verificationSendTime = new Date().getTime();
			String smsContent = "【KB社区】感谢注册KernelBase，您验证码为" + verificationCode + "，请在10分钟内正确输入，如非本人操作，请忽略此短信。";
			// 发送验证码
			try {
				SmsJsonVo sms = IndustrySMS.execute(mobile, smsContent);
				if (sms != null && sms.getRespCode().equals("00000")) { // 发送成功
					msg.put("msg", "验证码已发送到您的手机，请注意查收！");
					msg.put("stauts", "success");
					session.setAttribute("verificationCode", verificationCode);
					session.setAttribute("verificationSendTime", verificationSendTime);
				} else {
					msg.put("msg", "验证码发送失败，请确认手机无误后重新发送！");
					msg.put("stauts", "error");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msg.put("msg", "发送验证码时发生错误，请重试！");
				msg.put("stauts", "error");
			}

		} else {
			msg.put("msg", "请检查手机号正确后重新获取验证码！");
			msg.put("stauts", "error");
		}
		return msg;

	}

	/**
	 * 判断用户是否已登录
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/isLogined", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public boolean isLogined(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		return user != null;
	}

	/**
	 * 获取是否允许登陆后访问，没有登陆或remberme登陆的可以访问
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/isAccessWithLogined", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public boolean isAccessWithLogined(HttpServletRequest request) {
		boolean flag = true;
		if (isLogined(request)) {
			Boolean isRemberedLogin = (Boolean) request.getSession().getAttribute("isRemberedLogin");
			if (isRemberedLogin == null || !isRemberedLogin) {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 获取登陆账户名称
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUsername", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Map<String, String> getUsername(HttpServletRequest request) {
		Map<String, String> msg = new HashMap<String, String>();
		if (!isLogined(request)) {
			msg.put("stauts", "error");
		} else {
			User user = (User) request.getSession().getAttribute("user");
			msg.put("username", user.getUsername());
			msg.put("userId", user.getUserId().toString());
			msg.put("stauts", "success");
		}
		return msg;
	}

	/**
	 * 获取登陆用户的信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserinfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public User getUserinfo(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {

			User userinfo = userService.getUserinfo(user.getUserId());
			userinfo.setPassword(null); // 隐藏用户密码
			String phone = user.getPhone(); // 手机号处理
			userinfo.setPhone(phone.substring(0, 3) + "******" + phone.substring(9));
			return userinfo;

		} else {
			return null;
		}

	}

	/**
	 * 发表评论
	 * 
	 * @param reply
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/commentTopic", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Map<String, String> comment(HttpServletRequest request, TopicReply reply) {
		Map<String, String> msg = new HashMap<String, String>();

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			msg.put("data", "请登录后再进行发表！");
			msg.put("stauts", "error");
			return msg;
		}
		if (reply == null || reply.getContent() == null || reply.getTopicId() == null) {
			msg.put("data", "缺少参数！");
			msg.put("stauts", "error");
		} else {
			if (reply.getReplyToUserId() == null) {
				Integer replyToUserId = topicService.getTopicByTopicId(reply.getTopicId()).getUserId();
				if (replyToUserId == null) {
					msg.put("data", "数据异常!请重新发表！！！code:reply uid null");
					msg.put("stauts", "error");
					return msg;
				}
				reply.setReplyToUserId(replyToUserId);
			}

			reply.setReplytime(new Date());
			reply.setStatus(1); // 保留
			reply.setUserId(user.getUserId());

			boolean result = topicService.commentWithTopic(reply);
			if (result) {
				msg.put("data", "发表成功！");
				msg.put("stauts", "success");
			} else {
				msg.put("data", "发表时发生异常！请重新发表！");
				msg.put("stauts", "error");
			}
		}
		return msg;
	}

	/**
	 * 修改用户信息 
	 * @param request
	 * @param userinfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserinfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String updateUserinfo(HttpServletRequest request, Userinfo userinfo) {
		User user = (User) request.getSession().getAttribute("user");
		String msg = "error";
		if(user == null){
			return "nologin";
		}
		if(userinfo != null){
			//用户信息处理
			userinfo.setUserId(user.getUserId());
			String gender = userinfo.getGender();
			if(gender != null){
				if("0".equals(gender)){
					userinfo.setGender("男");
				}else if("1".equals(gender)){
					userinfo.setGender("女");
				}else{
					userinfo.setGender(null);
				}
			}
			if(userService.updateUserByUserId(userinfo) > 0){
				msg = "success";
			}
		}
			
		return msg;
	}

	@ResponseBody
	@RequestMapping(value="/publish", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public Map<String, Object> publish(HttpServletRequest request, String title,Integer catoreyId,String content){
		Map<String, Object> msg = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		if(user == null){
			msg.put("data", "请重新登陆后再发表文章！");
			msg.put("stauts", "error");
			return msg;
		}
		if(title == null || catoreyId == null || content == null || "".equals(catoreyId) || "".equals(title) || "".equals(content)){
			msg.put("data", "参数错误！");
			msg.put("stauts", "error");
		}else{
			Topic topic = new Topic();
			TopicContent topicContent = new TopicContent();
			
			topicContent.setContent(content);
			
			topic.setTitle(title);
			topic.setCatoreyId(catoreyId);
			topic.setUserId(user.getUserId());
			topic.setTopicContent(topicContent);
			topic.setPublishtime(new Date());
			topic.setReplycount(0);
			topic.setStatus(1);
			topic.setLikecount(0);
			
			Integer result = topicService.publish(topic);
			if(result > 0 ){
				msg.put("data", "发表成功！");
				msg.put("stauts", "success");
			}else{
				msg.put("data", "发表失败！");
				msg.put("stauts", "error");
			}
		}
		return msg;
	}
	
	@ResponseBody
	@RequestMapping(value="/delete", method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public Map<String, Object> delete(HttpServletRequest request, Integer topicId){
		Map<String, Object> msg = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		if(user == null){
			msg.put("data", "请重新登陆后再发表文章！");
			msg.put("stauts", "error");
			return msg;
		}
		if(topicId == null  || "".equals(topicId)){
			msg.put("data", "参数错误！");
			msg.put("stauts", "error");
		}else{
			Topic topic = topicService.getTopicByTopicId(topicId);
			if(!user.getUserId().equals(topic.getUserId())){
				msg.put("data", "非法操作！");
				msg.put("stauts", "error");
				return msg;
			}else{
				Integer result = topicService.deleteByTopicId(topicId);
				if(result >0){
					msg.put("data", "删除成功！");
					msg.put("stauts", "success");
				}else{
					msg.put("data", "删除失败！");
					msg.put("stauts", "error");
				}
			}
			
			
			
		}
		return msg;
	}
	/** 
     * 用于处理Date类型参数处理
     * @return 
     */  
    @InitBinder  
    protected  void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  


    }
}