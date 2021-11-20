package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class FechaNoSeleccionadaException extends SigevaExceptions{


	/**
	 * 
	 */
	private static final long serialVersionUID = -5726565430775988368L;

	public FechaNoSeleccionadaException() {
		super(HttpStatus.CONFLICT, "Para consultar las citas debe seleccionar una fecha válida.");
	}
}