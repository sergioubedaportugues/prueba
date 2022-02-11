package edu.uclm.esi.carreful.exceptions;

import org.springframework.http.HttpStatus;

public class CarrefulPassNotEqualException extends CarrefulException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3403192798289995195L;

	public CarrefulPassNotEqualException() {
		super(HttpStatus.CONFLICT, "Las contraseñas no coinciden.");
	
	}

}
