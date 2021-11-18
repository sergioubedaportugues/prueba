package uclm.grupo2.sigeva.http;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uclm.grupo2.sigeva.dao.CitasDAO;
import uclm.grupo2.sigeva.dao.TokenDAO;
import uclm.grupo2.sigeva.exceptions.TokenBorradoException;

@RestController
@RequestMapping("vistaSanitario")
public class SanitarioController {

	@Autowired
	private CitasDAO cita;
	
	@Autowired
	private TokenDAO tokenLogin;

	// ConsultarCitas
	// AplicarVacuna

	
	/*@GetMapping("/getCitasPorCentro/{login}")
	public List<Citas> getCitasPorCentro(){
		return cita.getByDiaAndCs(fecha, );
	}*/

	private void validarLogin() throws TokenBorradoException {
		if(tokenLogin.findAll().isEmpty())
			throw new TokenBorradoException();
		}
	
}
