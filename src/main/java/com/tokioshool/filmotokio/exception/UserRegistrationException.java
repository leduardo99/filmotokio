package com.tokioshool.filmotokio.exception;

public class UserRegistrationException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UserRegistrationException() {
		super();
	}
	
	public UserRegistrationException(String message) {
		super(message);
	}
}
