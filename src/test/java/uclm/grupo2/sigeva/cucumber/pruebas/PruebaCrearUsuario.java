package uclm.grupo2.sigeva.cucumber.pruebas;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.http.LoginController;
import uclm.grupo2.sigeva.http.UsuarioController;
import uclm.grupo2.sigeva.model.CentroSalud;
import uclm.grupo2.sigeva.model.Usuario;

public class PruebaCrearUsuario {
	
	@Autowired
	private UsuarioController UserCtrl;
	
	@Autowired
	private UsuarioDAO user;
	
	@Autowired
	private LoginController LoginCtrl;

	@Given("nuevo usuario con {string}, {string}, {string}, {string}, {string}, {string} y {string}")
	public void nuevo_usuario_con_y(String login, String password, String nombre, String apellidos, String telefono, String dni, String rol) {
		
		Optional<Usuario> optUser = user.findByLogin("administrador");
		LoginCtrl.iniciarSesion(optUser.get());
		
		Usuario user = new Usuario();
		login= "Antonio32"; password="Patata68"; nombre="Antonio"; apellidos="Fernandez"; telefono="888888878"; dni="98888888A"; rol="Admin"; 
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
		assertEquals("Usuario con id: "+user.getId(),UserCtrl.insertarUsuario(user));
		UserCtrl.borrarUsuario(user);

	}

	@Then("se ha creado un usuario")
	public void se_ha_creado_un_usuario() {

	}
	
}