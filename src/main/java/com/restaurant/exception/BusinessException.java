package com.restaurant.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 2963743674118319936L;
	private String message;

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

