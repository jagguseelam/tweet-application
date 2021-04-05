package com.tweet_app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Seelam Jagadeeswara Reddy
 *
 */
public class TweetUtil {

	public boolean validateDashboardUserInput(String userInput, String callFrom) {
		if (null != userInput) {
			if (callFrom.equalsIgnoreCase(TweetConstants.HOME)
					&& (Integer.parseInt(userInput) > 3 || Integer.parseInt(userInput) < 1)) {
				return false;
			} else if (callFrom.equalsIgnoreCase(TweetConstants.LOGIN)
					&& (Integer.parseInt(userInput) > 6 || Integer.parseInt(userInput) < 1)) {
				return false;
			} else if ((callFrom.equalsIgnoreCase(TweetConstants.REGN_AND_LOGIN)
					|| callFrom.equalsIgnoreCase(TweetConstants.SUGGESTIONS_TO_THE_USER))
					&& (Integer.parseInt(userInput) > 1 || Integer.parseInt(userInput) < 1)) {
				return false;
			}
			return true;
		} else {
			System.out.println("Invalid Input Option");
			return false;
		}

	}

	public String getInputFromUser() {
		Scanner scanner = new Scanner(System.in);
		String userInput = scanner.next();
		return userInput;
	}

	public boolean validateEmail(String email) {
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public boolean validatePassword(String password) {
		String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	public boolean validateDateOfBirth(String dateOfBirth) {
		String regex = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher((CharSequence) dateOfBirth);
		return matcher.matches();
	}

	public Date formatStringToDate(String date) {
		if (null != date && date != "") {
			DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date newDate;
			try {
				newDate = sourceFormat.parse(date);
			} catch (ParseException e) {
				throw new RuntimeException("Parse Exception while String to Date conversion");
			}
			return newDate;
		} else {
			return null;
		}

	}

	public java.sql.Date convertUtilDateToSqlDate(Date utilDate) {
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
	}

}
