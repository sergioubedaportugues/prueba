package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class StockInsuficienteException extends SigevaExceptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2709126431663173399L;

	public StockInsuficienteException() {
		super(HttpStatus.CONFLICT, "No quedan vacunas en el centro.");
	}
}
