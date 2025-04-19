package com.golden.raspbery.awards.infrastructure;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	public BusinessException(String message, Object... args) {
		super(message.formatted(args));
	}
}
