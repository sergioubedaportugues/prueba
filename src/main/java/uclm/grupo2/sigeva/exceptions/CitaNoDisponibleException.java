package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class CitaNoDisponibleException extends SigevaExceptions{


	/**
	 * 
	 */
	private static final long serialVersionUID = -8872045472464304208L;

	public CitaNoDisponibleException() {
		super(HttpStatus.CONFLICT, "No hay citas disponibles para la fecha seleccionada.");
	}
}
