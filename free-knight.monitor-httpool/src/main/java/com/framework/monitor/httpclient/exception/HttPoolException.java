package com.framework.monitor.httpclient.exception;

public class HttPoolException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public HttPoolException(String message) {
		super(message);
	}
	
	public HttPoolException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
