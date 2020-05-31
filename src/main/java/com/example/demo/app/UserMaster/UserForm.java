package com.example.demo.app.UserMaster;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
public class UserForm {

	public int UserId;
	@Size(min = 1, max = 20, message="１字以上20字以下で入力してください。")
	public String UserName;
	@NotNull
	@Size(max=200,message="200字以下で入力してください。")
	public String contents;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Past(message="日付が未来に設定されています。")
	public LocalDate UserBirthDay;

	@NotNull(message="誕生年を選択してください")
	public String birthDayYear;
	@NotNull(message="誕生月を選択してください")
	public String birthDayMonth;
	@NotNull(message="誕生日を選択してください")
	public String birthDay;
	@NotNull(message="年齢を入力してください")
	public String UserAge;
	public String UserSex;
	public String selectedRadio;

	//ページネーション用
    private long page;
    private long size;

	//コンストラクタ
	public UserForm() {};

	//引数ありのコンストラクタ
	public UserForm(int UserId,
			String UserName,
			String contents,
			LocalDate UserBirthDay,
			String BirthDayYear,
			String BirthDayMonth,
			String BirthDay,
			String UserAge,
			String UserSex,
			String selectedRadio) {
		this.UserId = UserId;
		this.UserName = UserName;
		this.contents = contents;
		this.UserBirthDay = UserBirthDay;
		this.birthDayYear = BirthDayYear;
		this.birthDayMonth = BirthDayMonth;
		this.birthDay = BirthDay;
		this.UserAge = UserAge;
		this.UserSex = UserSex;
		this.selectedRadio = selectedRadio;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public LocalDate getUserBirthDay() {
		return UserBirthDay;
	}

	public String getBirthDayYear() {
		return birthDayYear;
	}
	public void setBirthDayYear(String birthDayYear) {
		this.birthDayYear = birthDayYear;
	}

	public String getBirthDayMonth() {
		return birthDayMonth;
	}

	public void setBirthDayMonth(String birthDayMonth) {
		this.birthDayMonth = birthDayMonth;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public void setUserBirthDay(LocalDate userBirthDay) {
		UserBirthDay = userBirthDay;
	}

	public String getUserAge() {
		return UserAge;
	}

	public void setUserAge(String userAge) {
		UserAge = userAge;
	}

	public String getUserSex() {
		return UserSex;
	}

	public void setUserSex(String userSex) {
		UserSex = userSex;
	}

	public String getSelectedRadio() {
		return selectedRadio;
	}

	public void setSelectedRadio(String selectedRadio) {
		this.selectedRadio = selectedRadio;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

}
