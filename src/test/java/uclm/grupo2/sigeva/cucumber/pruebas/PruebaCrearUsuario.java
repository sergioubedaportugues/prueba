package uclm.grupo2.sigeva.cucumber.pruebas;

import org.springframework.beans.factory.annotation.Autowired;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import uclm.grupo2.sigeva.http.UsuarioController;
import uclm.grupo2.sigeva.model.Usuario;

public class PruebaCrearUsuario {
	
	@Autowired
	private UsuarioController UserCtrl;

	@Given("nuevo usuario con {string}, {string}, {string}, {string}, {string}, {string} y {string}")
	public void nuevo_usuario_con_y(String login, String password, String nombre, String apellidos, String telefono, String dni, String rol) {
		Usuario user = new Usuario();
		login= "Paquito8"; password="Patata8"; nombre="Paco"; apellidos="Fernandez"; telefono="888888888"; dni="88888888A"; rol="Admin"; 
		user.setLogin(login);
		user.setNombre(nombre);
		user.setPassword(password);
		user.setApellidos(apellidos);
		user.setDni(dni);
		user.setTelefono(telefono);
		user.setRol(rol);
		UserCtrl.insertarUsuario(user);
	}

	@Then("Se ha creado un usuario ")
	public void se_ha_creado_un_usuario(String string) {

	}
	
}
