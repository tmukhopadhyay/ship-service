package com.hpc.ship.exceptions;

public class ShipNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 41812393267276865L;

	public ShipNotFoundException() {
		super();
	}

	public ShipNotFoundException(String errorMessage) {
		super(errorMessage);
	}

	public ShipNotFoundException(String errorMessage, Throwable ex) {
		super(errorMessage, ex);
	}
}
