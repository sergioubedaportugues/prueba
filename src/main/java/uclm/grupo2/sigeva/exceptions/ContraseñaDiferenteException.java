package uclm.grupo2.sigeva.exceptions;

public class ContraseñaDiferenteException extends Exception{
	
	public ContraseñaDiferenteException () {
		 
		//Constructor
	}

	@Override
	public String getMessage() {
		return "Las Contraseñas introducidas no coinciden";
	}
}
