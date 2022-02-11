package edu.uclm.esi.carreful.exceptions;

import org.springframework.http.HttpStatus;

public class CarrefulException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7418484215113578717L;
	private  final HttpStatus status;
	private final String  message;

	public CarrefulException(HttpStatus status, String mensaje) {
		this.status = status;
		this.message = mensaje;
	}

	public HttpStatus getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}

	
	
	
	
}
