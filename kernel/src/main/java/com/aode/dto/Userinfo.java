package com.aode.dto;

import java.util.Date;

public class Userinfo {

	private Integer UserinfoId;
	
	private String gender;
	
	private Date birthday;
	
	private String address;
	
	private Integer userId;

	
	public Integer getUserinfoId() {
		return UserinfoId;
	}

	public void setUserinfoId(Integer userinfoId) {
		UserinfoId = userinfoId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
