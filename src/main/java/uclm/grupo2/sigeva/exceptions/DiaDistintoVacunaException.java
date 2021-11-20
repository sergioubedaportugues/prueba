package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class DiaDistintoVacunaException extends SigevaExceptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7107637749028217289L;

	public DiaDistintoVacunaException() {
		super(HttpStatus.CONFLICT, "Solo puedes vacunar pacientes del día actual.");
	}
}
