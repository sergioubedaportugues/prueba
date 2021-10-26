package uclm.grupo2.sigeva.exceptions;

public class CredencialesInvalidasException extends Exception{
	
	public CredencialesInvalidasException() {
		
		//Constructor
	}

	@Override
	public String getMessage() {
		return "Las Credenciales son Invalidas";
	}
}
