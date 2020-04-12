package com.example.demo.app.userMaster;

import java.time.LocalDateTime;

public class UserForm {

	public String name;
	public String contents;
	public LocalDateTime birthDay;
	public String age;
	public String sex;


	//コンストラクタ
	public UserForm() {
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public LocalDateTime getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(LocalDateTime birthDay) {
		this.birthDay = birthDay;
	}


}
