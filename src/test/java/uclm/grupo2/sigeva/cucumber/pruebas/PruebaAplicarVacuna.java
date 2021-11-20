package uclm.grupo2.sigeva.cucumber.pruebas;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import uclm.grupo2.sigeva.dao.CentroSaludDAO;
import uclm.grupo2.sigeva.dao.CitasDAO;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.exceptions.DiaDistintoVacunaException;
import uclm.grupo2.sigeva.exceptions.StockInsuficienteException;
import uclm.grupo2.sigeva.exceptions.TokenBorradoException;
import uclm.grupo2.sigeva.exceptions.VacunaAplicadaException;
import uclm.grupo2.sigeva.http.CentroSaludController;
import uclm.grupo2.sigeva.http.CitasController;
import uclm.grupo2.sigeva.http.LoginController;
import uclm.grupo2.sigeva.http.SanitarioController;
import uclm.grupo2.sigeva.model.CentroSalud;
import uclm.grupo2.sigeva.model.Citas;
import uclm.grupo2.sigeva.model.Usuario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class PruebaAplicarVacuna {
	
	@Autowired
	private UsuarioDAO user;
	
	@Autowired
	private LoginController LoginCtrl;
	
	@Autowired
	private CitasController CitasCtrl;
	
	@Autowired
	private SanitarioController SanitarioCtrl;
	
	@Autowired
	private CentroSaludController CentroCtrl;
	
	@Autowired
	private CitasDAO cita;
	
	@Autowired
	private CentroSaludDAO center;
	
	private static final String DDMMAA = "dd-MM-yyyy";
	private static DateTimeFormatter formatterDia = DateTimeFormatter.ofPattern(DDMMAA);
	
	@Given("un usuario sanitario loggeado")
	public void un_usuario_sanitario_loggeado() throws TokenBorradoException, DiaDistintoVacunaException, StockInsuficienteException, VacunaAplicadaException {
		
		Optional<Usuario> optUser = user.findByLogin("paciente");
		LoginCtrl.iniciarSesion(optUser.get());	
		CitasCtrl.insertarCita();
		List <Citas> citasHostia = cita.getByPacienteOrderByNumCitaAsc(optUser.get());
		citasHostia.get(0).setAplicada(true);
		List <Citas> c = cita.getByPacienteOrderByNumCitaAsc(optUser.get());
		
		LocalDate date = LocalDate.parse(c.get(0).getDia(), formatterDia);
		date = date.minusDays(1);
		c.get(0).setDia(date.format(DateTimeFormatter.ofPattern(DDMMAA)));
		cita.save(c.get(0));
		LoginCtrl.cerrarSesion();
		
		
		Optional<Usuario> optUser2 = user.findByLogin("sanitario");
		LoginCtrl.iniciarSesion(optUser2.get());
		
		assertEquals("Vacuna aplicada.",SanitarioCtrl.aplicarVacuna(c.get(0)));
		cita.deleteAll(citasHostia);
		CentroSalud centro = optUser2.get().getCs();
		int vacunaAumentada = Integer.parseInt(centro.getNumVacunas());
		centro.setNumVacunas(String.valueOf(vacunaAumentada));
		optUser.get().setDosis(0);
		user.save(optUser.get());

		LoginCtrl.cerrarSesion();
		
		Optional<Usuario> optUser3 = user.findByLogin("administrador");
		LoginCtrl.iniciarSesion(optUser3.get());	
		CentroCtrl.modificarCentro(centro);
		

		LoginCtrl.cerrarSesion();
	}

	@Then("se aplica una vacuna")
	public void se_aplica_una_vacuna() {
	}	
}
