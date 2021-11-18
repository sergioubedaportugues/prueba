package uclm.grupo2.sigeva.cucumber.pruebas;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.http.LoginController;
import uclm.grupo2.sigeva.model.Usuario;

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
		
		Optional<Usuario> optUser = user.findByLogin("administrador");
		Usuario userLoggeado = LoginCtrl.iniciarSesion(optUser.get());	
		assertEquals(userLoggeado.getLogin(),optUser.get().getLogin());
		
	}

	@Then("acceso a la funcionalidad")
	public void acceso_a_la_funcionalidad() {

	}

}
