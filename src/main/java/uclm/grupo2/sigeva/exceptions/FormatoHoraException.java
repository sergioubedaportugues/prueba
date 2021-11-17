package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class FormatoHoraException extends SigevaExceptions {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3026876019101211700L;

	public FormatoHoraException() {
		super(HttpStatus.CONFLICT, "Formato de fecha 'dd-mm-yyyy' y formato de hora 'hh:mm'");
	}
}
