package com.auros.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class DateUtility {
	public static String userDateFormat(String dateFormat) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		String strDate = formatter.format(date);
		return strDate.toString();

		/*
		 * dd/MM/yyyy dd-M-yyyy hh:mm:ss dd MMMM yyyy zzzz dd MMM yyyy HH:mm:ss z
		 * ddMMyyhhmmss ddMMyyhh:mm:ss .etc
		 */
	}

	/**
	 * getFetuareDate method have two parameters first parameter= How many days need
	 * to added from today date second parameters = date format like "dd/MM/YYYY"
	 * example: getFetuareDate("7", "dd/MM/YYYY"); here "7" represents number of
	 * Days to added to Today and "dd/MM/YYYY" represents date format so to will
	 * give format 31/12/2021 provide
	 *
	 */
	public static String getFetuareDate(String howManyDays, String dateFormat) {
		LocalDateTime date = LocalDateTime.now();
		LocalDateTime featureDate = date.plusDays(Long.valueOf(howManyDays));
		DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat);
		String userDate = featureDate.format(format);
		return userDate;
	}
	
	public static String generateRandomNumber(int length) {
		Date date = new Date();
		SimpleDateFormat formatter;
		String randamData = null;
		if (length == 11) {
			formatter = new SimpleDateFormat("yyDDD");
			randamData = formatter.format(date).toString();
			randamData= randamData+DateUtility.getRandomNumber(111111, 999999);
			return randamData;
		} else if (length == 10) {
			formatter = new SimpleDateFormat("yyDDD");
			randamData = formatter.format(date).toString();
			randamData= randamData+DateUtility.getRandomNumber(11111, 99999);
			return randamData;
		}
		return randamData;
	}
	
	public static int getRandomNumber(int mini_Range, int max_Range) {
		int randomNum = (int) (Math.random() * (max_Range - mini_Range + 1) + mini_Range);
		return randomNum;
	}


}
