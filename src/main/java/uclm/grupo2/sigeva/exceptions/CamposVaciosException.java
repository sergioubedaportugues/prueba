package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class CamposVaciosException extends SigevaExceptions {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5758764762465910315L;

	public CamposVaciosException() {
		super(HttpStatus.CONFLICT, "Debes rellenar todos los campos.");
	}
}
