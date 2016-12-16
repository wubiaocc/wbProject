package com.finance.communication.service.server.realm;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 6
 *
 * 7
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a> 8
 */

public class CaptchaException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public CaptchaException() {

		super();

	}

	public CaptchaException(String message, Throwable cause) {

		super(message, cause);

	}

	public CaptchaException(String message) {

		super(message);

	}

	public CaptchaException(Throwable cause) {

		super(cause);

	}

}
