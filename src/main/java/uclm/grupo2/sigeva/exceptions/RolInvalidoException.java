package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class RolInvalidoException extends SigevaExceptions {
	/**
	 * 
	 */
	private static final long serialVersionUID = 580111827245910810L;

	public RolInvalidoException() {
		super(HttpStatus.CONFLICT, "El rol debe ser uno de los siguientes:\n1. Sanitario.\n2. Admin.\n3. Paciente.");
	}
}
