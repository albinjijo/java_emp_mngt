package com.litmus;

import java.util.regex.Pattern;

public class Validator {
	
	public static boolean isValidCSVFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) return false;

        if (!fileName.toLowerCase().endsWith(".csv")) return false;

       return true;
    }
	
	private static final Pattern EMAIL_PATTERN =
	        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Pattern NAME_PATTERN =
        Pattern.compile("^[A-Za-z]{2,50}$");

    private static final Pattern  PHONE_PATTERN =
        Pattern.compile("^[6-9]\\d{9}$");

    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidSalary(int salary) {
        return salary >= 0;
    }

}
