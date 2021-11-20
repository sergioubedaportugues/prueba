package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class TokenBorradoException extends SigevaExceptions{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4213083663472025783L;

	public TokenBorradoException() {
		super(HttpStatus.CONFLICT, "No existe ningun usuario con sesión iniciada.");
	}
}
