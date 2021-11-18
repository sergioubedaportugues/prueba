package uclm.grupo2.sigeva.http;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uclm.grupo2.sigeva.dao.CitasDAO;
import uclm.grupo2.sigeva.dao.TokenDAO;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.exceptions.TokenBorradoException;
import uclm.grupo2.sigeva.model.Citas;
import uclm.grupo2.sigeva.model.Token;
import uclm.grupo2.sigeva.model.Usuario;

@RestController
@RequestMapping("vistaSanitario")
public class SanitarioController {

	@Autowired
	private CitasDAO cita;
	
	@Autowired
	private TokenDAO tokenLogin;
	
	@Autowired
	private UsuarioDAO user;

	// ConsultarCitas
	// AplicarVacuna
	
	private static final String DDMMAA = "dd-MM-yyyy";
	private static DateTimeFormatter formatterDia = DateTimeFormatter.ofPattern(DDMMAA);
	
	@GetMapping("/getCitasPorCentro")
	public List<Citas> getCitasPorCentro() throws TokenBorradoException{
		validarLogin();
		Token tokSanitario = tokenLogin.findAll().get(0);
		Usuario usu = user.getByLogin(tokSanitario.getLogin()).get(0);
		LocalDate date = LocalDate.now();
		String formattedLocalDate = date.format(formatterDia);		
		return cita.getByDiaAndCs(formattedLocalDate,usu.getCs());
	}

	private void validarLogin() throws TokenBorradoException {
    	List<Usuario> usuarios = user.getByLogin(tokenLogin.findAll().get(0).getLogin());
    	Usuario usu = usuarios.get(0);
        if(tokenLogin.findAll().isEmpty() || !usu.getRol().equals("Sanitario"))
            throw new TokenBorradoException();
        }
}
