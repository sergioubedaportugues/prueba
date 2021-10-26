package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class UsuarioDuplicadoException extends SigevaExceptions {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9075534457136836890L;

	public UsuarioDuplicadoException() {
		super(HttpStatus.CONFLICT, "El usuario ya se encuentra en la base de datos.");
	}
}
