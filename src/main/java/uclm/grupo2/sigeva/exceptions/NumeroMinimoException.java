package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class NumeroMinimoException extends SigevaExceptions {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8638961855643487944L;

	public NumeroMinimoException() {
		super(HttpStatus.CONFLICT, "No pueden existir valores negativos en los campos.");
	
	}
}