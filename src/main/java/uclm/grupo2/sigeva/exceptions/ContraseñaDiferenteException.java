package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class ContraseñaDiferenteException extends SigevaExceptions{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4115372759446723808L;

	public ContraseñaDiferenteException() {
		super(HttpStatus.CONFLICT, "Las Contraseñas introducidas no coinciden");
	}
}
