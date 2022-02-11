package edu.uclm.esi.carreful.exceptions;

import org.springframework.http.HttpStatus;

public class CarrefulLoginException extends CarrefulException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3403192798289995195L;

	public CarrefulLoginException() {
		super(HttpStatus.UNAUTHORIZED, "Credenciales invalidas");
	
	}

}
