package com.example.demo.util;

import java.time.LocalDate;

public class CalendarUtil {


	/**
	 * yyyy-MM-ddをyyyy年MM月dd日に直します。
	 * @param birthDay
	 * @return 年月日
	 */
	public static String LocalDateTimeToNenGappi(LocalDate birthDay){

		String userBirthDay ;
		StringBuilder sb = new StringBuilder();
		sb.append(birthDay.getYear()).append("年").
			append(birthDay.getMonthValue()).append("月").
			append(birthDay.getDayOfMonth()).append("日");
		return userBirthDay = sb.toString();

	}



}
