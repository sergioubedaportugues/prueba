package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class CentroConCitasException extends SigevaExceptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2416594441175343626L;

	public CentroConCitasException() {
		super(HttpStatus.CONFLICT, "El centro no se puede eliminar porque tiene citas asignadas.");
	}
}
