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
		uDTO.setIdDTO(optUser.get().getId());
		uDTO.setLoginDTO(optUser.get().getLogin());
		uDTO.setPasswordDTO(optUser.get().getPassword());
		uDTO.setNombreDTO(optUser.get().getNombre());
		uDTO.setApellidosDTO(optUser.get().getApellidos());
		uDTO.setTelefonoDTO(optUser.get().getTelefono());
		uDTO.setDniDTO(optUser.get().getDni());
		uDTO.setRolDTO(optUser.get().getRol());
		uDTO.setCsDTO(optUser.get().getCs());
		uDTO.setDosisDTO(optUser.get().getDosis());
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
		uDTO1.setIdDTO(user.getId());
		uDTO1.setLoginDTO(user.getLogin());
		uDTO1.setPasswordDTO(user.getPassword());
		uDTO1.setNombreDTO(user.getNombre());
		uDTO1.setApellidosDTO(user.getApellidos());
		uDTO1.setTelefonoDTO(user.getTelefono());
		uDTO1.setDniDTO(user.getDni());
		uDTO1.setRolDTO(user.getRol());
		uDTO1.setCsDTO(user.getCs());
		uDTO1.setDosisDTO(user.getDosis());
		
		
		assertEquals("Usuario con id: "+user.getId(),UserCtrl.insertarUsuario(uDTO1));
		UserCtrl.borrarUsuario(uDTO1);

	}

	@Then("se ha creado un usuario")
	public void se_ha_creado_un_usuario() {

	}
	
}