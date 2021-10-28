package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class AccesoDenegadoException extends SigevaExceptions {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4027902561416116007L;

	public AccesoDenegadoException() {
		super(HttpStatus.CONFLICT, "No tiene permitido acceso a esta funcionalidad");
	
	}
}
