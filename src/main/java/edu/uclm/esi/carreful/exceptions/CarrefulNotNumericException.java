package edu.uclm.esi.carreful.exceptions;

import org.springframework.http.HttpStatus;

public class CarrefulNotNumericException extends CarrefulException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3403192798289995195L;

	public CarrefulNotNumericException() {
		super(HttpStatus.CONFLICT, "El precio y el stock deben ser valores numéricos.");
	
	}

}
