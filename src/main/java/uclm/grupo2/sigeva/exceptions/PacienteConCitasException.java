package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class PacienteConCitasException extends SigevaExceptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1557631467175902903L;

	public PacienteConCitasException() {
		super(HttpStatus.CONFLICT, "El centro no se puede modificar porque el paciente tiene citas asignadas.");
	}
}
