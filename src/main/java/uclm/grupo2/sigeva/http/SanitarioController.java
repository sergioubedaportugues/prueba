package uclm.grupo2.sigeva.http;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uclm.grupo2.sigeva.dao.CentroSaludDAO;
import uclm.grupo2.sigeva.dao.CitasDAO;
import uclm.grupo2.sigeva.dao.TokenDAO;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.exceptions.DiaDistintoVacunaException;
import uclm.grupo2.sigeva.exceptions.FechaNoSeleccionadaException;
import uclm.grupo2.sigeva.exceptions.NoHayCitasException;
import uclm.grupo2.sigeva.exceptions.StockInsuficienteException;
import uclm.grupo2.sigeva.exceptions.TokenBorradoException;
import uclm.grupo2.sigeva.exceptions.VacunaAplicadaException;
import uclm.grupo2.sigeva.model.CentroSalud;
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
	
	@Autowired
	private CentroSaludDAO center;
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
	
	@GetMapping("/getConsultar")
	public List<Citas> getConsultar(@RequestParam String fecha) throws TokenBorradoException, FechaNoSeleccionadaException, NoHayCitasException{
		validarLogin();
		if(fecha.equals("NaN-NaN-NaN"))
			throw new FechaNoSeleccionadaException();
		Token tokSanitario = tokenLogin.findAll().get(0);
		Usuario usu = user.getByLogin(tokSanitario.getLogin()).get(0);
		if(cita.getByDiaAndCs(fecha,usu.getCs()).isEmpty())
			throw new NoHayCitasException();
		return cita.getByDiaAndCs(fecha,usu.getCs());
	}
	
	@PostMapping("/aplicarVacuna")
	public String aplicarVacuna(@RequestBody Citas c) throws TokenBorradoException, DiaDistintoVacunaException, StockInsuficienteException, VacunaAplicadaException {
		validarLogin();
		Optional<Citas> optCita = cita.findById(c.getId());
		if(optCita.isPresent()) {
			LocalDate date = LocalDate.now();
			String formattedLocalDate = date.format(formatterDia);
			if(optCita.get().isAplicada())
				throw new VacunaAplicadaException();
			if(!optCita.get().getDia().equals(formattedLocalDate))
				throw new DiaDistintoVacunaException();
			if(Integer.parseInt(optCita.get().getCs().getNumVacunas())<=0)
				throw new StockInsuficienteException();
			
			int vacunaDecrementada = Integer.parseInt(optCita.get().getCs().getNumVacunas())-1;
			
			List <Citas> listCitas = cita.getByPacienteOrderByNumCitaAsc(c.getPaciente());

			
			CentroSalud preCs = optCita.get().getCs();
			preCs.setNumVacunas(String.valueOf(vacunaDecrementada));
			center.save(preCs);
			
			Usuario preUsu = optCita.get().getPaciente();
			preUsu.setDosis(optCita.get().getPaciente().getDosis()+1);
			preUsu.setCs(preCs);
			user.save(preUsu);		
			
			Citas preCita = optCita.get();
			preCita.setAplicada(true);
			preCita.setPaciente(preUsu);
			preCita.setCs(preCs);
			cita.save(preCita);
			
			List <Usuario> cambiarCsUsu = user.findAll();
			for (int i=0; i<user.findAll().size();i++) {
				cambiarCsUsu.get(i).setCs(preCs);
				user.save(cambiarCsUsu.get(i));
			}
			
			List <Citas> cambiarCsCitas = cita.findAll();
			for (int i=0; i<cita.findAll().size();i++) {
				cambiarCsCitas.get(i).getPaciente().setCs(preCs);
				cambiarCsCitas.get(i).setCs(preCs);
				cita.save(cambiarCsCitas.get(i));
			}
			
              listCitas.get(1).setCs(preCs);
               listCitas.get(1).setPaciente(preUsu);
               listCitas.get(1).getPaciente().setCs(preCs);
                cita.save(listCitas.get(1));
			return "Vacuna aplicada.";
		}
		return null;
	}
	
	private void validarLogin() throws TokenBorradoException {
		if(tokenLogin.findAll().isEmpty())
			throw new TokenBorradoException();

    	List<Usuario> usuarios = user.getByLogin(tokenLogin.findAll().get(0).getLogin());
    	Usuario usu = usuarios.get(0);
        if(!usu.getRol().equals("Sanitario"))
            throw new TokenBorradoException();
        }
}
