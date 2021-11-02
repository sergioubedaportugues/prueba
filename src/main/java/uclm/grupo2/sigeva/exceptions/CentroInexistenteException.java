package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class CentroInexistenteException extends SigevaExceptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2850856470453024646L;

	public CentroInexistenteException() {
		super(HttpStatus.CONFLICT, "El centro de salud no se encuentra en la base de datos.");
	}
}
