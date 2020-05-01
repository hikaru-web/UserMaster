package com.example.demo.domain;

import java.sql.Timestamp;
import java.time.LocalDate;

public class User {
	private Long UserId;
	private String UserName;
	private Long UserAge;
	private String UserSex;
	private LocalDate UserBirthDay;
	private String contents;
	private Timestamp CreateTime;
	private Timestamp EditTime;


	public Long getUserId() {
		return UserId;
	}
	public void setUserId(Long userId) {
		UserId = userId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public Long getUserAge() {
		return UserAge;
	}
	public void setUserAge(Long userAge) {
		UserAge = userAge;
	}
	public String getUserSex() {
		return UserSex;
	}
	public void setUserSex(String userSex) {
		UserSex = userSex;
	}
	public LocalDate getUserBirthDay() {
		return UserBirthDay;
	}
	public void setUserBirthDay(LocalDate UserBirthDay) {
		this.UserBirthDay = UserBirthDay;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Timestamp getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Timestamp createTime) {
		CreateTime = createTime;
	}
	public Timestamp getEditTime() {
		return EditTime;
	}
	public void setEditTime(Timestamp editTime) {
		EditTime = editTime;
	}



}
