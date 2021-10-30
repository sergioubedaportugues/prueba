package uclm.grupo2.sigeva.http;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import uclm.grupo2.sigeva.dao.CentroSaludDAO;
import uclm.grupo2.sigeva.dao.CitasDAO;
import uclm.grupo2.sigeva.model.CentroSalud;
import uclm.grupo2.sigeva.model.Citas;

@RestController
@RequestMapping("gestionCitas")
public class CitasController {

	@Autowired
	private CitasDAO cita;
	
	@Autowired
	private CentroSaludDAO center;	
	
	@PostMapping("/insertCita")
	public String insertarCita() {
		try {
			boolean insertada = false;
			
			List<CentroSalud> centros = center.findAll();
			CentroSalud random = centros.get(new Random().nextInt(centros.size()));
			
			Citas citaNueva = new Citas();
			citaNueva.setCs(random);
			
			LocalDate date = LocalDate.now();
			LocalTime time = LocalTime.now();
			
			citaNueva.setDia(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			citaNueva.setHoras(time.format(DateTimeFormatter.ofPattern("HH:mm")));
			
			citaNueva.setNombreCentro(random.getNombre());
			
			while (insertada==false) {
				
				if(cita.getByDiaAndNombreCentroAndHorasStartingWith(citaNueva.getDia(), citaNueva.getCs().getNombre(), citaNueva.getHoras().substring(0,2)).size() < citaNueva.getCs().getCupo()) {
					cita.save(citaNueva);
					insertada = true;
				} else {
					//time = time.plusHours(1);
					//HAY QUE HACERLO PARA QUE EN VEZ DE DÍAS TAMBIEN CONTROLE HORAS, AHORA MISMO SOLO PUEDES METER 3 CITAS EN CADA HORA POR CENTRO.
					date = date.plusDays(1);
					citaNueva.setDia(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
				}
			}
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
		return "Centro con id:";
			
	}
	
	@GetMapping("/findAllCitas")
	public List<Citas> getCitas(){
		return cita.findAll();
	}
	
	
	@GetMapping("/findAllCitas/{id}")
	public Optional<Citas> getCita(@PathVariable String id){
		return cita.findById(id);
	}
	
	/*@DeleteMapping("/delete/{id}")
	public String deleteUsuarios(@PathVariable String id) {
		user.deleteById(id);
		return "Usuario eliminado con id: "+id;
	}*/
	
}