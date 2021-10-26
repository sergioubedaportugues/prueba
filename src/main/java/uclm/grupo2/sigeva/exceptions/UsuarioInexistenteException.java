package uclm.grupo2.sigeva.exceptions;

public class UsuarioInexistenteException extends Exception{
	
	public UsuarioInexistenteException() {
		 
		//Constructor
	}

	@Override
	public String getMessage() {
		return "El usuario introducido no existe ";
	}
}
