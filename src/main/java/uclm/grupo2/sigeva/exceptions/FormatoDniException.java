package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class FormatoDniException extends SigevaExceptions {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2914150037327896003L;

	public FormatoDniException() {
		super(HttpStatus.CONFLICT, "El formato del DNI deben ser 8 números y 1 letra.");
	}
}
