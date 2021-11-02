package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class SigevaExceptions extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7903868388951902298L;
	private  final HttpStatus status;
	private final String  message;

	public SigevaExceptions(HttpStatus status, String mensaje) {
		this.status = status;
		this.message = mensaje;
	}

	public HttpStatus getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
}

