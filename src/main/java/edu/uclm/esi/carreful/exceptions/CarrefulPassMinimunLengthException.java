package edu.uclm.esi.carreful.exceptions;

import org.springframework.http.HttpStatus;

public class CarrefulPassMinimunLengthException extends CarrefulException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3403192798289995195L;

	public CarrefulPassMinimunLengthException() {
		super(HttpStatus.CONFLICT, "La contraseña debe tener como mínimo 8 caracteres.");
	
	}

}
