package edu.uclm.esi.carreful.exceptions;

import org.springframework.http.HttpStatus;

public class CarrefulPriceException extends CarrefulException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3403192798289995195L;

	public CarrefulPriceException() {
		super(HttpStatus.CONFLICT, "El producto no tiene precio.");
	
	}

}
