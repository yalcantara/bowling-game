package com.jobsity.bowling.utils;

public class AppUtils {
	
	public static void checkParamNotNull(String name, Object value) {
		_checkParamNotNull(value, "The parameter '" + name + "' can not be null.");
	}
	
	public static void checkParamNotEmpty(String name, String value) {
		checkParamNotNull(name, value);
		value = strip(value);
		
		if (value == null) {
			throw new IllegalArgumentException("The parameter '" + name + "' can not be empty.");
		}
	}
	
	private static void _checkParamNotNull(Object value, String message) {
		if (value == null) {
			throw new NullPointerException(message);
		}
	}
	
	public static void checkParamIsPositive(String name, long value) {
		if (value < 0) {
			throw new IllegalArgumentException(
					"The parameter '" + name + "' must be positive. Got: " + value + ".");
		}
	}
	
	public static void checkParamBetween(String name, long fromInclusive, long toInclusive,
										 long value) {
		if (value < fromInclusive || value > toInclusive) {
			throw new IllegalArgumentException(
					"The parameter '" + name + "' must be between [" + fromInclusive + ", " +
							toInclusive + "]. Got: " + value + ".");
		}
	}
	
	public static boolean isNullOrEmpty(String str){
		return strip(str) == null;
	}
	
	public static String strip(String str) {
		if (str == null) {
			return null;
		}
		
		String s = str.trim();
		
		if (s.isEmpty()) {
			return null;
		}
		
		return s;
	}
	
	
}
