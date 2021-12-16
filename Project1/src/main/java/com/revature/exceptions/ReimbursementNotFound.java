package com.revature.exceptions;

public class ReimbursementNotFound extends Exception {
	
	ReimbursementNotFound() {
		super();
	}

	public ReimbursementNotFound(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReimbursementNotFound(String message, Throwable cause) {
		super(message, cause);
	}

	public ReimbursementNotFound(String message) {
		super(message);
	}

	public ReimbursementNotFound(Throwable cause) {
		super(cause);
	}
	
	

}
