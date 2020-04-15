package com.example.demo.app.UserMaster;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
public class UserForm {

	@Size(min = 1, max = 20, message="Please input 20 characters or less")
	public String name;
	@NotNull
	public String contents;
	@NotNull(message="誕生日を入力してください")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Past(message="日付が未来に設定されています。")
	public LocalDate birthDay;
	@NotNull(message="年齢を入力してください")
	public String age;
	@NotNull(message="性別を選択してください")
	public String sex;


	//コンストラクタ
	public UserForm() {};

	//引数ありのコンストラクタ
	public UserForm(String name,String contents,LocalDate birthDay,String age,String sex) {
		this.name = name;
		this.contents = contents;
		this.birthDay = birthDay;
		this.sex = sex;
	};

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

	public LocalDate getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
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


}
