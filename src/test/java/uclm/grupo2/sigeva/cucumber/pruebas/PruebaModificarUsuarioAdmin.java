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
import uclm.grupo2.sigeva.model.UsuarioDTO;
import io.cucumber.java.en.Then;

public class PruebaModificarUsuarioAdmin {
	
	@Autowired
	private UsuarioController UserCtrl;
	
	@Autowired
	private UsuarioDAO user;
	
	@Autowired
	private LoginController LoginCtrl;
	
	@Given("en la vista administrador {string},{string}, {string}, {string}, {string}, {string} y {string}")
	public void en_la_vista_administrador_y(String login, String password, String nombre, String apellidos, String telefono, String dni, String rol) {
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
		login= "Antonio68"; password="Patata68"; nombre="Antonio"; apellidos="Fernandez"; telefono="888888878"; dni="98888888A"; rol="Admin"; 

		String loginN= "Ramon68";String passwordN="Patatita1";String nombreN="Ramon"; 
		String apellidosN="Galera"; String telefonoN="888888778"; String dniN="98878888A"; 
		
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
		
		uDTO.setId(user.getId());
		uDTO.setLogin(user.getLogin());
		uDTO.setPassword(user.getPassword());
		uDTO.setNombre(user.getNombre());
		uDTO.setApellidos(user.getApellidos());
		uDTO.setTelefono(user.getTelefono());
		uDTO.setDni(user.getDni());
		uDTO.setRol(user.getRol());
		uDTO.setCs(user.getCs());
		uDTO.setDosis(user.getDosis());
		
		UserCtrl.insertarUsuario(uDTO);
		
		user.setLogin(loginN);
		user.setNombre(nombreN);
		user.setPassword(passwordN);
		user.setApellidos(apellidosN);
		user.setDni(dniN);
		user.setTelefono(telefonoN);
		centro.setNombre("Miguelturra23M");
		user.setCs(centro);
		
		uDTO.setId(user.getId());
		uDTO.setLogin(user.getLogin());
		uDTO.setPassword(user.getPassword());
		uDTO.setNombre(user.getNombre());
		uDTO.setApellidos(user.getApellidos());
		uDTO.setTelefono(user.getTelefono());
		uDTO.setDni(user.getDni());
		uDTO.setRol(user.getRol());
		uDTO.setCs(user.getCs());
		uDTO.setDosis(user.getDosis());
		
		assertEquals("Usuario modificado",UserCtrl.modificarUsuario(uDTO));
		UserCtrl.borrarUsuario(uDTO);
	}

	@Then("se han modificado los datos")
	public void se_han_modificado_los_datos() {

	}

}