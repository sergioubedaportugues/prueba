package edu.uclm.esi.carreful.exceptions;

import org.springframework.http.HttpStatus;

public class CarrefulMinimumNumberException extends CarrefulException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3403192798289995195L;

	public CarrefulMinimumNumberException() {
		super(HttpStatus.CONFLICT, "No pueden existir valores negativos en los campos.");
	
	}

}
