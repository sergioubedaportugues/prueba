package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class CitaInexistenteException extends SigevaExceptions{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3411375963326070853L;

	public CitaInexistenteException() {
		super(HttpStatus.CONFLICT, "La cita no se encuentra en la base de datos.");
	}
}
