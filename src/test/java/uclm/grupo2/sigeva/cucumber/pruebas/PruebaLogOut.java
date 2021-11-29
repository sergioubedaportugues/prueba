package uclm.grupo2.sigeva.cucumber.pruebas;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import uclm.grupo2.sigeva.dao.TokenDAO;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.http.LoginController;
import uclm.grupo2.sigeva.model.Token;
import uclm.grupo2.sigeva.model.Usuario;
import uclm.grupo2.sigeva.model.UsuarioDTO;



public class PruebaLogOut {
	
	@Autowired
	private UsuarioDAO user;
	
	@Autowired
	private TokenDAO token;
	
	@Autowired
	private LoginController LoginCtrl;
	
	@Given("el identificador {string}")
	public void el_identificador(String string) {
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
		LoginCtrl.cerrarSesion();
		List <Token> optToken = token.findAll();
		assertEquals(true,optToken.isEmpty());
		

	}

	@Then("acceder a ventana login")
	public void acceder_a_ventana_login() {

	}

}
