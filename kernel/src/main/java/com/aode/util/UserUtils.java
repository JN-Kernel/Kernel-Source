package com.aode.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.aode.dto.User;
import com.aode.service.IUserService;

public class UserUtils {

	/**
	 * 通过登录账户取得用户信息
	 * 
	 * @param loginName
	 *            用户名/手机号/邮箱
	 * @return user
	 */
	public static User getUserByLoginName(IUserService userService,String param) {
		User user = null;
		// 验证用户名/邮箱/手机登陆
		if (RegexUtils.checkUsername(param)) {

			user = userService.getByUsername(param);

		} else if (RegexUtils.checkEmail(param)) {

			user = userService.getByEmail(param);

		} else if (RegexUtils.checkPhone(param)) {

			user = userService.getByPhone(param);

		}

		return user;
	}
	
	
	/**
	 * 检验用户指定属性合法性
	 * @param user
	 * @param items 检查的属性列表
	 * @return 返回检查后的布尔列表集合
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Map<String,Boolean> checkUser(User user,List<String> items) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if(items.isEmpty() || user == null){
			return null;
		}
		Map<String,Boolean> result = new HashMap<String ,Boolean>();
		Class<?> regexUtilsClazz = Class.forName("com.aode.util.RegexUtils");
		Class<?> userClazz = user.getClass();
		Iterator<String> itemsIterator = items.iterator();
		while(itemsIterator.hasNext()){
			boolean booleanResult = false;
			String item = (String) itemsIterator.next();
			String itemCase = item.substring(0,1).toUpperCase() + item.substring(1);	//首字母大写
			Method getMethod = userClazz.getMethod("get"+itemCase);
			Object value = getMethod.invoke(user);
			if(value != null){
				Method checkMethod = regexUtilsClazz.getMethod("check"+itemCase, String.class);
				booleanResult = (Boolean) checkMethod.invoke(null, value);
			}
			result.put(item, booleanResult);
			System.out.println(item+":"+booleanResult);
		}
		return result;
	}
	

	/**
	 * 验证用户名/手机号/邮箱是否已经注册
	 * @param userService
	 * @param user
	 * @return 返回空集则未被注册
	 */
	public static String checkIsRegister(IUserService userService,User user){
		StringBuffer result = new StringBuffer();
		if(getUserByLoginName(userService,user.getUsername()) != null){
			result.append("用户名");
		}
		if(getUserByLoginName(userService, user.getEmail()) != null){
			result.append(result.length() <= 0 ?"邮箱":"、邮箱");
		}
		if(getUserByLoginName(userService, user.getPhone()) != null){
			result.append(result.length() <= 0 ?"手机":"、手机");
		}
		return result.length() <= 0 ? null:result.append("已经被注册！").toString();	
	}
}
