package com.github.mangelt.lab.exception;

public class AppException extends RuntimeException {

	private static final long serialVersionUID = 3877067655170426698L;
	
	public AppException(String message, Throwable e) {
		super(message, e);
	}
}
