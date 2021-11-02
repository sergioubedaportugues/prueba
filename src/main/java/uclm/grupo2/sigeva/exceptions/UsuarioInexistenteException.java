package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class UsuarioInexistenteException extends SigevaExceptions{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6216941878556677681L;

	public UsuarioInexistenteException() {
		super(HttpStatus.CONFLICT, "El usuario no se encuentra en la base de datos.");
	}
}
