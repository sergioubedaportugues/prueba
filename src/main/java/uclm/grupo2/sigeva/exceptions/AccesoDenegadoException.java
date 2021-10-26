package uclm.grupo2.sigeva.exceptions;

public class AccesoDenegadoException extends Exception{
	
	public AccesoDenegadoException() {
		
		//Constructor
	}
	
	@Override
	public String getMessage() {
		return "No tiene permitido acceso a esta funcionalidad";
	}

}
