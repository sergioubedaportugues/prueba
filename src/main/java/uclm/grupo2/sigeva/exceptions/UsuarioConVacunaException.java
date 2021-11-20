package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class UsuarioConVacunaException extends SigevaExceptions {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1201992521539822445L;

	public UsuarioConVacunaException() {
		super(HttpStatus.CONFLICT, "El usuario tiene una vacuna aplicada, no es posible eliminarlo.");
	
	}
}
