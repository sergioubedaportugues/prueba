package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class NoHayCitasException extends SigevaExceptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5193776093492470880L;

	public NoHayCitasException() {
		super(HttpStatus.CONFLICT, "No hay citas disponibles para la fecha seleccionada.");
	}
}
