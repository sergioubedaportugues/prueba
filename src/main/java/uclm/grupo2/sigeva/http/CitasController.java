package uclm.grupo2.sigeva.http;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import uclm.grupo2.sigeva.dao.CentroSaludDAO;
import uclm.grupo2.sigeva.dao.CitasDAO;
import uclm.grupo2.sigeva.dao.TokenDAO;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.exceptions.CitaInexistenteException;
import uclm.grupo2.sigeva.exceptions.CitaNoDisponibleException;
import uclm.grupo2.sigeva.exceptions.CitasMaximasException;
import uclm.grupo2.sigeva.exceptions.FechaMaximaException;
import uclm.grupo2.sigeva.exceptions.FechasIncorrectasException;
import uclm.grupo2.sigeva.exceptions.FormatoHoraException;
import uclm.grupo2.sigeva.exceptions.NoModificableException;
import uclm.grupo2.sigeva.exceptions.TokenBorradoException;
import uclm.grupo2.sigeva.exceptions.UsuarioInexistenteException;
import uclm.grupo2.sigeva.exceptions.VacunaAplicadaException;
import uclm.grupo2.sigeva.model.CentroSalud;
import uclm.grupo2.sigeva.model.Citas;
import uclm.grupo2.sigeva.model.CitasDTO;
import uclm.grupo2.sigeva.model.Usuario;
import uclm.grupo2.sigeva.model.Token;


@RestController
@RequestMapping("vistaPaciente")
public class CitasController {

	@Autowired
	private CitasDAO cita;
	
	@Autowired
	private CentroSaludDAO center;
	
	@Autowired
	private UsuarioDAO user;
	
	@Autowired
	private TokenDAO tokenLogin;
	
	private static final String DDMMAA = "dd-MM-yyyy";
	private static final String HHMM = "HH:mm";
	private static DateTimeFormatter formatterDia = DateTimeFormatter.ofPattern(DDMMAA);
	private static DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern(HHMM);
	
	@PostMapping("/insertCita")
	public String insertarCita() {
		try {
			validarLogin();
			Token tokpaciente = tokenLogin.findAll().get(0);
			
			Usuario paciente = user.getByLogin(tokpaciente.getLogin()).get(0);
			
			List<CentroSalud> centros = center.findByNombre(paciente.getCs().getNombre());
			CentroSalud cs = centros.get(0);
			
			LocalDate date = LocalDate.now();
			date = date.plusDays(1);
			LocalTime time = LocalTime.now();
			
			if(cita.getByPacienteOrderByNumCitaAsc(paciente).isEmpty()) {
				Citas citaUno = controlCitas(paciente,cs,1,date,time);
				LocalDate dateDos = LocalDate.parse(citaUno.getDia() , formatterDia);
				LocalTime timeDos = LocalTime.parse(citaUno.getHoras() , formatterHora);
				controlCitas(paciente,cs,2,dateDos,timeDos);		
			} else if (cita.getByPacienteOrderByNumCitaAsc(paciente).size()==1) {
				List<Citas> listaCitPac = cita.getByPacienteOrderByNumCitaAsc(paciente);
				Citas citaUno = listaCitPac.get(0);
				LocalDate dateUno = LocalDate.parse(citaUno.getDia() , formatterDia);
				LocalTime timeUno = LocalTime.parse(citaUno.getHoras() , formatterHora);
				controlCitas(paciente,cs,2,dateUno,timeUno);	
			} else {
				throw new CitasMaximasException();
			}
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}	
		return "Cita creada";
	}
	
	@DeleteMapping("/deleteCita")
	public void borrarCita(@RequestBody CitasDTO cDTO) {
		Citas c = citaDTOcambio(cDTO);
		try {
			validarLogin();
			Optional<Citas> optCita = cita.findById(c.getId());
			if (optCita.isPresent()) {
				controlarAplicada(c);
				List <Citas> listadoCitas = cita.getByPacienteOrderByNumCitaAsc(c.getPaciente());
				if (listadoCitas.size()==2 && c.getNumCita()==1) {
					cita.deleteById(c.getId());
					for(int i=listadoCitas.size()-2; i>=0;i--) {
						if(listadoCitas.get(i).getNumCita()==1)
							listadoCitas.remove(i);
					}
					listadoCitas.get(0).setNumCita(1);
					cita.save(listadoCitas.get(0));
				} else {
					cita.deleteById(c.getId());
				}
			} else
				throw new UsuarioInexistenteException();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@PostMapping("/modifyCita")
	public String modificarCita(@RequestBody CitasDTO cDTO) {
		Citas c = citaDTOcambio(cDTO);
		try {
			validarLogin();

			Optional<Citas> optCita = cita.findById(c.getId());
			
			if (optCita.isPresent()) {
				
				controlarAplicada(c);
				
				if(!comprobarFormatorHoras(c.getHoras()) || !tiempoHoras(c.getHoras()) || !validarDias(c.getDia()) || !controlDias(c.getDia()))
					throw new FormatoHoraException();				
				
				if(c.getPaciente().getDosis()>=c.getNumCita())
					throw new NoModificableException();
				
				List <Citas> listadoCitas = cita.getByPacienteOrderByNumCitaAsc(c.getPaciente());
				if(c.getNumCita()==listadoCitas.get(0).getNumCita()) {
					listadoCitas.get(0).setDia(c.getDia());
					listadoCitas.get(0).setHoras(c.getHoras());
				} else {
					listadoCitas.get(1).setDia(c.getDia());
					listadoCitas.get(1).setHoras(c.getHoras());
				}
				Duration diferencia = Duration.between(LocalDate.parse(listadoCitas.get(0).getDia(),formatterDia).atStartOfDay(), LocalDate.parse(listadoCitas.get(1).getDia(),formatterDia).atStartOfDay());
				long diferenciaDias = diferencia.toDays();
				administrarFechas(diferenciaDias, listadoCitas, c);
				} else 
					throw new CitaInexistenteException();
			} catch(Exception e) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
			}
			return "Cita modificada";
		}
	
	@GetMapping("/mostrarCitasPedidas")
	public List<Citas> mostrarCitasPedidas() throws TokenBorradoException{
		validarLogin();
		Token tokpaciente = tokenLogin.findAll().get(0);
		Usuario usu = user.getByLogin(tokpaciente.getLogin()).get(0);
		return cita.getByPacienteOrderByNumCitaAsc(usu);
	}
	
	public Citas controlCitas(Usuario paciente, CentroSalud cs, int num, LocalDate date, LocalTime time) throws FechaMaximaException {
		boolean insertada = false;
		Citas citaNueva = new Citas();
		citaNueva.setCs(cs);

		if (num==2)
			date = date.plusDays(21);
		citaNueva.setDia(date.format(DateTimeFormatter.ofPattern(DDMMAA)));
		if (date.isAfter(LocalDate.parse("10-01-2022",formatterDia)) && num==2)
			throw new FechaMaximaException();
		citaNueva.setHoras(time.format(DateTimeFormatter.ofPattern(HHMM)));
		citaNueva.setPaciente(paciente);
		citaNueva.setNumCita(num);

		int vueltas = 0;

		while (!insertada) {

			if (cita.getByDiaAndCsAndHorasStartingWith(citaNueva.getDia(), citaNueva.getCs(),
					citaNueva.getHoras().substring(0, 2)).size() < Integer.parseInt(citaNueva.getCs().getCupo())
					&& LocalTime.parse(citaNueva.getHoras())
							.compareTo(LocalTime.parse(citaNueva.getCs().getfFin())) < 0
					&& LocalTime.parse(citaNueva.getHoras())
							.compareTo(LocalTime.parse(citaNueva.getCs().getfInicio())) > 0) {

				cita.save(citaNueva);
				insertada = true;
			} else {
				if (vueltas == 24) {
					date = date.plusDays(1);
					citaNueva.setDia(date.format(DateTimeFormatter.ofPattern(DDMMAA)));
					vueltas = 0;
				}
				time = time.plusHours(1);
				citaNueva.setHoras(time.format(DateTimeFormatter.ofPattern(HHMM)));
				vueltas++;
			}
		}
		return citaNueva;
	}
	
	public void controlModificarCitas(Usuario paciente, CentroSalud cs, int num, LocalDate date, LocalTime time) throws FechaMaximaException, CitaNoDisponibleException {
		Citas citaNueva = new Citas();
		citaNueva.setCs(cs);

		citaNueva.setDia(date.format(DateTimeFormatter.ofPattern(DDMMAA)));
		if (date.isAfter(LocalDate.parse("10-01-2022",formatterDia)) && num==1)
			throw new FechaMaximaException();
		citaNueva.setHoras(time.format(DateTimeFormatter.ofPattern(HHMM)));
		citaNueva.setPaciente(paciente);
		citaNueva.setNumCita(num);

		if (cita.getByDiaAndCsAndHorasStartingWith(citaNueva.getDia(), citaNueva.getCs(),
				citaNueva.getHoras().substring(0, 2)).size() < Integer.parseInt(citaNueva.getCs().getCupo())
				&& LocalTime.parse(citaNueva.getHoras())
						.compareTo(LocalTime.parse(citaNueva.getCs().getfFin())) < 0
				&& LocalTime.parse(citaNueva.getHoras())
						.compareTo(LocalTime.parse(citaNueva.getCs().getfInicio())) > 0) {
			cita.save(citaNueva);
		}
		else
			throw new CitaNoDisponibleException();
	}
	
	public void administrarFechas(long diferenciaDias, List<Citas> listadoCitas, Citas c) throws FechaMaximaException, CitaNoDisponibleException, FechasIncorrectasException {
		if(listadoCitas.size()==2 && c.getNumCita()==1 && LocalDate.parse(listadoCitas.get(1).getDia(),formatterDia).isAfter(LocalDate.parse(listadoCitas.get(0).getDia(),formatterDia))) {
			controlModificarCitas(c.getPaciente(),c.getCs(),c.getNumCita(),LocalDate.parse(listadoCitas.get(0).getDia(), formatterDia), LocalTime.parse(listadoCitas.get(0).getHoras(), formatterHora));
			cita.deleteById(c.getId());
		} else if(listadoCitas.size()==2 && c.getNumCita()==2 && diferenciaDias>=21) {
			controlModificarCitas(c.getPaciente(),c.getCs(),c.getNumCita(),LocalDate.parse(listadoCitas.get(1).getDia(), formatterDia), LocalTime.parse(listadoCitas.get(1).getHoras(), formatterHora));
			cita.deleteById(c.getId());
		} else
			throw new FechasIncorrectasException();
	}
	
	
	private static boolean comprobarFormatorHoras(String h) {
		if (h.length() != 5)
			return false;
		for (int i = 0; i < h.length() - 1; i++) {
			if (i == 2) {
				if (h.charAt(i) != ':')
					return false;
			} else {
				if (!Character.isDigit(h.charAt(i))) {
					return false;
				}
			}
		}
		return true;
	}
	
	private static boolean tiempoHoras(String horas) {
		boolean valido = false;
		String[] h = horas.split(":");
		if(Integer.parseInt(h[0])<24 && (Integer.parseInt(h[1])<60)) {
			valido = true;
		}
		return valido;
	}
	private static boolean validarDias(String dia) {
		if (dia.length() != 10)
			return false;
		for (int i = 0; i < dia.length() - 1; i++) {
			if (i == 2 ||i==5) {
				if (dia.charAt(i) != '-')
					return false;
			} else {
				if (!Character.isDigit(dia.charAt(i))) {
					return false;
				}
			}
		}
		return true;
	}
	private static boolean controlDias(String dia) {
		boolean valido = false;
		String[] h = dia.split("-");
		if(Integer.parseInt(h[0])<32 && (Integer.parseInt(h[1])<13) && Integer.parseInt(h[2])<2023) {
			valido = true;
		}
		return valido;
	}
	
	private void validarLogin() throws TokenBorradoException {
		if(tokenLogin.findAll().isEmpty())
			throw new TokenBorradoException();

    	List<Usuario> usuarios = user.getByLogin(tokenLogin.findAll().get(0).getLogin());
    	Usuario usu = usuarios.get(0);
        if(!usu.getRol().equals("Paciente"))
            throw new TokenBorradoException();
        }
	
	private void controlarAplicada(Citas c) throws VacunaAplicadaException {
		if(c.isAplicada())
			throw new VacunaAplicadaException();
	}
	private Citas citaDTOcambio(CitasDTO cDTO) {
		Citas c = new Citas();
		c.setId(cDTO.getId());
		c.setHoras(cDTO.getHoras());
		c.setDia(cDTO.getDia());
		c.setPaciente(cDTO.getPaciente());
		c.setCs(cDTO.getCs());
		c.setNumCita(cDTO.getNumCita());
		c.setAplicada(cDTO.isAplicada());
		return c;
	}
}