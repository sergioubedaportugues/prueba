package edu.uclm.esi.carreful.exceptions;

import org.springframework.http.HttpStatus;

public class CarrefulCamposVaciosException extends CarrefulException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3403192798289995195L;

	public CarrefulCamposVaciosException() {
		super(HttpStatus.CONFLICT, "Debes rellenar todos los campos.");
	
	}

}
