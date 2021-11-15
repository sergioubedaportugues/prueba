package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class FechaMaximaException extends SigevaExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -194585194365679897L;

	public FechaMaximaException() {
		super(HttpStatus.CONFLICT, "La fecha de la última cita no puede ser superior al 31 de Enero de 2022.");
	}
}
