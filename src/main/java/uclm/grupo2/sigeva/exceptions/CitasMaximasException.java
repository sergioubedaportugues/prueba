package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class CitasMaximasException extends SigevaExceptions {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4874673394815394947L;

	public CitasMaximasException() {
		super(HttpStatus.CONFLICT, "El usuario ya tiene 2 citas asignadas.");
	}
}
