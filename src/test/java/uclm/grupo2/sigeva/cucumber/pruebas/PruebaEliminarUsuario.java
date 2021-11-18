package uclm.grupo2.sigeva.cucumber.pruebas;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.java.en.Given;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.http.LoginController;
import uclm.grupo2.sigeva.http.UsuarioController;
import uclm.grupo2.sigeva.model.CentroSalud;
import uclm.grupo2.sigeva.model.Usuario;
import io.cucumber.java.en.Then;



public class PruebaEliminarUsuario {
	
	@Autowired
	private UsuarioController UserCtrl;
	
	@Autowired
	private UsuarioDAO user;
	
	@Autowired
	private LoginController LoginCtrl;
	
	@Given("un usuario con {string}, {string}, {string}, {string}, {string}, {string} y {string}")
	public void un_usuario_con_y(String login, String password, String nombre, String apellidos, String telefono, String dni, String rol) {
		
		Optional<Usuario> optUser = user.findByLogin("administrador");
		LoginCtrl.iniciarSesion(optUser.get());
		
		Usuario user = new Usuario();
		login= "Antonio65"; password="Patata68"; nombre="Antonio"; apellidos="Fernandez"; telefono="888888878"; dni="98888888A"; rol="Admin"; 
		CentroSalud centro = new CentroSalud();
		centro.setNombre("MiguelturraTest23");
		centro.setDireccion("Avenida Parque 8");
		centro.setNumVacunas("7780");
		centro.setfInicio("09:30");
		centro.setfFin("14:00");
		centro.setFranja("6");
		centro.setCupo("5");
		
		user.setLogin(login);
		user.setNombre(nombre);
		user.setPassword(password);
		user.setApellidos(apellidos);
		user.setDni(dni);
		user.setTelefono(telefono);
		user.setRol(rol);
		user.setCs(centro);
		UserCtrl.insertarUsuario(user);
		assertEquals("Usuario eliminado",UserCtrl.borrarUsuario(user));
	}

	@Then("se ha eliminado el usuario")
	public void se_ha_eliminado_el_usuario() {

	}
}