package edu.uclm.esi.carreful.exceptions;

import org.springframework.http.HttpStatus;

public class CarrefulDuplicatedException extends CarrefulException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3403192798289995195L;

	public CarrefulDuplicatedException() {
		super(HttpStatus.CONFLICT, "El elemento ya se encuentra en la base de datos.");
	
	}

}
