package uclm.grupo2.sigeva.cucumber.pruebas;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
public class PruebaCrearUsuario {

	@Given("nuevo usuario con {string}, {string}, {string}, {string}, {string}, {string} y {string}")
	public void nuevo_usuario_con_y(String login, String password, String nombre, String apellidos, String telefono, String dni, String rol) {
		
		
	}

	@Then("crea un usuario {string}")
	public void crea_un_usuario(String string) {

	}
	
}
