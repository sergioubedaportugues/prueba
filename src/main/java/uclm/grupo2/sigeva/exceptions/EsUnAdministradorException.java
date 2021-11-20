package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class EsUnAdministradorException extends SigevaExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7187254307933780324L;

	public EsUnAdministradorException() {
		super(HttpStatus.CONFLICT, "No puedes borrar un administrador.");
	
	}
}
