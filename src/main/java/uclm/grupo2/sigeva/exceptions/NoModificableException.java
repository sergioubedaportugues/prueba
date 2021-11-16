package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class NoModificableException extends SigevaExceptions {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1201992521539822445L;

	public NoModificableException() {
		super(HttpStatus.CONFLICT, "La vacuna ya ha sido aplicada, no puedes modificar la cita.");
	
	}
}
