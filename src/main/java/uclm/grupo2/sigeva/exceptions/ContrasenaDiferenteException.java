package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class ContrasenaDiferenteException extends SigevaExceptions{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4115372759446723808L;

	public ContrasenaDiferenteException() {
		super(HttpStatus.CONFLICT, "Las password introducidas no coinciden");
	}
}