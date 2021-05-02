package com.jobsity.bowling.core;

public class InvalidScoreException extends BowlingException {

	public InvalidScoreException() {
	}

	public InvalidScoreException(String message) {
		super(message);
	}

	public InvalidScoreException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidScoreException(Throwable cause) {
		super(cause);
	}

	public InvalidScoreException(String message, Throwable cause, boolean enableSuppression,
								 boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
