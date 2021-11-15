package uclm.grupo2.sigeva.http;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import uclm.grupo2.sigeva.dao.CentroSaludDAO;
import uclm.grupo2.sigeva.dao.CitasDAO;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.exceptions.CitasMaximasException;
import uclm.grupo2.sigeva.exceptions.FechaMaximaException;
import uclm.grupo2.sigeva.exceptions.UsuarioInexistenteException;
import uclm.grupo2.sigeva.model.CentroSalud;
import uclm.grupo2.sigeva.model.Citas;
import uclm.grupo2.sigeva.model.Usuario;

@RestController
@RequestMapping("gestionCitas")
public class CitasController {

	@Autowired
	private CitasDAO cita;
	
	@Autowired
	private CentroSaludDAO center;
	
	@Autowired
	private UsuarioDAO user;
	
	private static final String DDMMAA = "dd-MM-yyyy";
	private static final String HHMM = "HH:mm";
	private static DateTimeFormatter formatterDia = DateTimeFormatter.ofPattern(DDMMAA);
	private static DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern(HHMM);
	
	@PostMapping("/insertCita")
	public String insertarCita() {
		try {			
			List<Usuario> pacientes = user.getByRol("Paciente");
			Usuario paciente = pacientes.get(new Random().nextInt(pacientes.size()));
			
			List<CentroSalud> centros = center.findByNombre(paciente.getCs().getNombre());
			CentroSalud cs = centros.get(0);
			
			LocalDate date = LocalDate.now();
			date = date.plusDays(1);
			LocalTime time = LocalTime.now();
			
			if(cita.getByPaciente(paciente).isEmpty()) {
				Citas citaUno = controlCitas(paciente,cs,1,date,time);
				LocalDate dateDos = LocalDate.parse(citaUno.getDia() , formatterDia);
				LocalTime timeDos = LocalTime.parse(citaUno.getHoras() , formatterHora);
				controlCitas(paciente,cs,2,dateDos,timeDos);		
			} else if (cita.getByPaciente(paciente).size()==1) {
				List<Citas> listaCitPac = cita.getByPaciente(paciente);
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
	public void borrarCita(@RequestBody Citas c) {
		try {
			Optional<Citas> optCita = cita.findById(c.getId());
			if (optCita.isPresent()) {
				List <Citas> listadoCitas = cita.getByPaciente(c.getPaciente());
				if (listadoCitas.size()==2 && c.getNumCita()==1) {
					cita.deleteById(c.getId());
					listadoCitas.get(1).setNumCita(1);
					cita.save(listadoCitas.get(1));
				} else {
					cita.deleteById(c.getId());
				}
			} else
				throw new UsuarioInexistenteException();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/findAllCitas")
	public List<Citas> getCitas(){
		return cita.findAll();
	}
	
	
	@GetMapping("/findAllCitas/{id}")
	public List<Citas> getCita(@PathVariable String id){
		return cita.getById(id);
		
	}
	
	@GetMapping("/mostrarCitasPedidas")
	public List<Citas> mostrarCitasPedidas(){
		List<Citas> misCitas = cita.findAll();
		if (misCitas.size()>2) {
			for(int i= misCitas.size()-3; i>=0 ;i--) {
				misCitas.remove(i);
			}
		}
		return misCitas;
	}
	
	public Citas controlCitas(Usuario paciente, CentroSalud cs, int num, LocalDate date, LocalTime time) throws FechaMaximaException {
		boolean insertada = false;
		Citas citaNueva = new Citas();
		citaNueva.setCs(cs);


		if (num==2)
			date = date.plusDays(21);
		citaNueva.setDia(date.format(DateTimeFormatter.ofPattern(DDMMAA)));
		if (date.isAfter(LocalDate.parse("10-01-2022")) && num==1)
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
}