package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class FormatoHoraException extends SigevaExceptions {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3026876019101211700L;

	public FormatoHoraException() {
		super(HttpStatus.CONFLICT, "El formato de la hora  deben ser HH:MM"+" Y "+"HH<24 MM<60 y La hora de cierre tiene que ser mayor a la de apertura");
	}
}
