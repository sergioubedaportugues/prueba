package uclm.grupo2.sigeva.cucumber.pruebas;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.http.LoginController;
import uclm.grupo2.sigeva.model.Usuario;
import uclm.grupo2.sigeva.model.UsuarioDTO;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;


public class PruebaInicioSesion {
	
	
	@Autowired
	private UsuarioDAO user;
	
	@Autowired
	private LoginController LoginCtrl;
	
	@Given("dados unos {string} y {string} correctos")
	public void dados_unos_y_correctos(String login, String password) {	
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
		Usuario userLoggeado = LoginCtrl.iniciarSesion(uDTO);	
		assertEquals(userLoggeado.getLogin(),optUser.get().getLogin());
		
	}

	@Then("acceso a la funcionalidad")
	public void acceso_a_la_funcionalidad() {

	}

}
