package com.revature.exceptions;

public class Unauthorized extends Exception {
	
	public Unauthorized() {
		super();
	}

	public Unauthorized(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public Unauthorized(String message, Throwable cause) {
		super(message, cause);
	}

	public Unauthorized(String message) {
		super(message);
	}

	public Unauthorized(Throwable cause) {
		super(cause);
	}
	
	

}
