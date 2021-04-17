package com.hpc.ship.exceptions;

public class InvalidPayloadException extends RuntimeException {

	private static final long serialVersionUID = 5749313669362395134L;

	public InvalidPayloadException() {
		super();
	}

	public InvalidPayloadException(String errorMessage) {
		super(errorMessage);
	}

	public InvalidPayloadException(String errorMessage, Throwable ex) {
		super(errorMessage, ex);
	}
}
