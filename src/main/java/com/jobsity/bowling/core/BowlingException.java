package com.jobsity.bowling.core;

public class BowlingException extends RuntimeException {
	
	
	public BowlingException() {
	}
	
	public BowlingException(String message) {
		super(message);
	}
	
	public BowlingException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BowlingException(Throwable cause) {
		super(cause);
	}
	
	public BowlingException(String message, Throwable cause, boolean enableSuppression,
							boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
