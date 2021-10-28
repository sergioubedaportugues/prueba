package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class CentroDuplicadoException extends SigevaExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 307280929851923605L;

	public CentroDuplicadoException() {
		super(HttpStatus.CONFLICT, "El centro ya se encuentra en la base de datos.");
	}
}
