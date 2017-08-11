package com.aode.util;

import java.util.regex.Pattern;

public class RegexUtils {

	 /**
     * 验证Email
     * @param email
     * @return 验证成功返回true，验证失败返回false
     */ 
    public static boolean checkEmail(String email) { 
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?"; 
        return Pattern.matches(regex, email); 
    } 
    
    
    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     * @param 手机号码
     * @return 验证成功返回true，验证失败返回false
     */ 
    public static boolean checkPhone(String phone) { 
        String regex = "(\\+\\d+)?1[34578]\\d{9}$"; 
        return Pattern.matches(regex,phone); 
    } 
    
    /**
     * 验证用户名,长度最小4位，最长32位，以字母开头，包含字母数字及下划线
     * @param 用户名
     * @return 验证成功返回true，验证失败返回false
     */ 
    public static boolean checkUsername(String username) { 
        String regex = "^[a-zA-Z]\\w{3,31}$"; 
        return Pattern.matches(regex, username); 
    } 
    
    /**
     * 验证密码,长度最小6位，最长20位，包含字母开头，包含字母数字及下划线
     * @param 密码
     * @return 验证成功返回true，验证失败返回false
     */ 
    public static boolean checkPassword(String password) { 
        String regex = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,20}$"; 
        return Pattern.matches(regex, password); 
    } 
}
