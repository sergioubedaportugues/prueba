package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class CredencialesInvalidasException extends SigevaExceptions{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2223472868723384395L;

	public CredencialesInvalidasException() {
		super(HttpStatus.CONFLICT, "Las credenciales son invalidas");
	}
}
