package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class FormatoHinicioHfinException extends SigevaExceptions {


	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463905876388196801L;

	public FormatoHinicioHfinException() {
		super(HttpStatus.CONFLICT, "La hora de cierre tiene que ser mayor a la de apertura");
	}
}
