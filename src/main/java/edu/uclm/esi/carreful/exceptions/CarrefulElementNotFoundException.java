package edu.uclm.esi.carreful.exceptions;

import org.springframework.http.HttpStatus;

public class CarrefulElementNotFoundException extends CarrefulException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3403192798289995195L;

	public CarrefulElementNotFoundException() {
		super(HttpStatus.NOT_FOUND, "El elemento no se encuentra en la base de datos.");
	
	}

}
