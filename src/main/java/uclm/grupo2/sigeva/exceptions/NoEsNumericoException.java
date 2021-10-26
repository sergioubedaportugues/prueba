package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class NoEsNumericoException extends SigevaExceptions {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9213950033196427643L;

	public NoEsNumericoException() {
		super(HttpStatus.CONFLICT, "El telefeono debe ser valores numéricos.");
	
	}
}
