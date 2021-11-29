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
import uclm.grupo2.sigeva.model.UsuarioDTO;

public class PruebaCrearUsuario {
	
	@Autowired
	private UsuarioController UserCtrl;
	
	@Autowired
	private UsuarioDAO user;
	
	@Autowired
	private LoginController LoginCtrl;

	@Given("nuevo usuario con {string}, {string}, {string}, {string}, {string}, {string} y {string}")
	public void nuevo_usuario_con_y(String login, String password, String nombre, String apellidos, String telefono, String dni, String rol) {
		UsuarioDTO uDTO= new UsuarioDTO();
		
		Optional<Usuario> optUser = user.findByLogin("administrador");
		uDTO.setId(optUser.get().getId());
		uDTO.setLogin(optUser.get().getLogin());
		uDTO.setPassword(optUser.get().getPassword());
		uDTO.setNombre(optUser.get().getNombre());
		uDTO.setApellidos(optUser.get().getApellidos());
		uDTO.setTelefono(optUser.get().getTelefono());
		uDTO.setDni(optUser.get().getDni());
		uDTO.setRol(optUser.get().getRol());
		uDTO.setCs(optUser.get().getCs());
		uDTO.setDosis(optUser.get().getDosis());
		LoginCtrl.iniciarSesion(uDTO);
		
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
		UsuarioDTO uDTO1= new UsuarioDTO();
		uDTO1.setId(user.getId());
		uDTO1.setLogin(user.getLogin());
		uDTO1.setPassword(user.getPassword());
		uDTO1.setNombre(user.getNombre());
		uDTO1.setApellidos(user.getApellidos());
		uDTO1.setTelefono(user.getTelefono());
		uDTO1.setDni(user.getDni());
		uDTO1.setRol(user.getRol());
		uDTO1.setCs(user.getCs());
		uDTO1.setDosis(user.getDosis());
		
		
		assertEquals("Usuario con id: "+user.getId(),UserCtrl.insertarUsuario(uDTO1));
		UserCtrl.borrarUsuario(uDTO1);

	}

	@Then("se ha creado un usuario")
	public void se_ha_creado_un_usuario() {

	}
	
}