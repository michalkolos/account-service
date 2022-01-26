package com.michalkolos.account.api.security;

import java.util.regex.Pattern;

public class Utilities {

//	private static final Pattern MAIL_REGEX_PATTERN = Pattern.compile(
//			"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
//        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");

	private static final Pattern MAIL_REGEX_PATTERN = Pattern.compile(
			"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@acme.com");


	public static boolean validateEmailAddress(String emailAddress) {
		return MAIL_REGEX_PATTERN.matcher(emailAddress).matches();
	}
}
