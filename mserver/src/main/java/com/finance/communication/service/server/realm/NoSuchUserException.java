package com.finance.communication.service.server.realm;

import org.apache.shiro.authc.AuthenticationException;

public class NoSuchUserException extends AuthenticationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchUserException() {

		super();

	}

	public NoSuchUserException(String message, Throwable cause) {

		super(message, cause);

	}

	public NoSuchUserException(String message) {

		super(message);

	}

	public NoSuchUserException(Throwable cause) {

		super(cause);

	}
}
