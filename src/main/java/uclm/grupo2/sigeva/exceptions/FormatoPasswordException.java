package uclm.grupo2.sigeva.exceptions;

import org.springframework.http.HttpStatus;

public class FormatoPasswordException extends SigevaExceptions{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2914150037327896003L;
	
	public FormatoPasswordException() {
		super(HttpStatus.CONFLICT, "La contraseña debe contener al menos 8 caracteres, 1 Mayuscula, 1 Minuscula y 1 Numero.");
	}

}
