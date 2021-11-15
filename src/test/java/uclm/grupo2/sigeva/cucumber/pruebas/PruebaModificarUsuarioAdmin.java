package uclm.grupo2.sigeva.cucumber.pruebas;

import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.java.en.Given;
import uclm.grupo2.sigeva.http.UsuarioController;
import uclm.grupo2.sigeva.model.Usuario;
import io.cucumber.java.en.Then;

public class PruebaModificarUsuarioAdmin {
	
	@Autowired
	private UsuarioController UserCtrl;
	
	@Given("en la vista administrador {string},{string}, {string}, {string}, {string}, {string} y {string}")
	public void en_la_vista_administrador_y(String login, String password, String nombre, String apellidos, String telefono, String dni, String rol) {
		Usuario user = new Usuario();
		login= "Antonio68"; password="Patata68"; nombre="Antonio"; apellidos="Fernandez"; telefono="888888878"; dni="98888888A"; rol="Admin"; 
		String loginN= "Ramon68";String passwordN="Patatita6";String nombreN="Ramon"; 
		String apellidosN="Galera"; String telefonoN="888888778"; String dniN="98878888A"; 
		
		
		user.setLogin(login);
		user.setNombre(nombre);
		user.setPassword(password);
		user.setApellidos(apellidos);
		user.setDni(dni);
		user.setTelefono(telefono);
		user.setRol(rol);
		UserCtrl.insertarUsuario(user);
		
		user.setLogin(loginN);
		user.setNombre(nombreN);
		user.setPassword(passwordN);
		user.setApellidos(apellidosN);
		user.setDni(dniN);
		user.setTelefono(telefonoN);
		assertEquals("Usuario modificado",UserCtrl.modificarUsuario(user));
		UserCtrl.borrarUsuario(user);
	}

	@Then("se han modificado los datos")
	public void se_han_modificado_los_datos() {

	}

}
