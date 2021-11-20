package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class VacunaAplicadaException extends SigevaExceptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8842727962939672189L;

	public VacunaAplicadaException() {
		super(HttpStatus.CONFLICT, "La vacuna ya fue aplicada.");
	}
}