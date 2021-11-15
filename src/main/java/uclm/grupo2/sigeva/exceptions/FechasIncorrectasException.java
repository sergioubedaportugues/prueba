package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class FechasIncorrectasException extends SigevaExceptions{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2644546288340161178L;

	public FechasIncorrectasException() {
		super(HttpStatus.CONFLICT, "La cita 1 debe ser anterior a la cita 2.");
	}
}
