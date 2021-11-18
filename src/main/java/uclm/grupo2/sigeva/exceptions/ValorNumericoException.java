package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class ValorNumericoException extends SigevaExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9223070767658503982L;

	public ValorNumericoException() {
		super(HttpStatus.CONFLICT, "El campo 'Número de Vacunas' debe ser un número entero positivo.");
	
	}

}
