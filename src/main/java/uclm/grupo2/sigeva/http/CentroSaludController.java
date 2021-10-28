package uclm.grupo2.sigeva.http;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import uclm.grupo2.sigeva.dao.CentroSaludDAO;
import uclm.grupo2.sigeva.exceptions.CamposVaciosException;
import uclm.grupo2.sigeva.exceptions.CentroDuplicadoException;
import uclm.grupo2.sigeva.exceptions.NumeroMinimoException;
import uclm.grupo2.sigeva.exceptions.UsuarioDuplicadoException;
import uclm.grupo2.sigeva.exceptions.ValorNumericoException;
import uclm.grupo2.sigeva.model.CentroSalud;

@RestController
@RequestMapping("gestionCentroSalud")
public class CentroSaludController {

	@Autowired
	private CentroSaludDAO center;
	
	@PostMapping("/insertCenter")
	public String insertarCentro(@RequestBody CentroSalud cs) {
		try {
			Optional<CentroSalud> optCenter = center.findByNombre(cs.getNombre());
			if (optCenter.isPresent())
				throw new CentroDuplicadoException();
			else {
				if(cs.getNombre().isEmpty())
					throw new CamposVaciosException();
				if(cs.getDireccion().isEmpty())
					throw new CamposVaciosException();
				if(cs.getNum_vacunas().isEmpty())
					throw new CamposVaciosException();
				if(!esNumericoEntero(cs.getNum_vacunas()))
					throw new ValorNumericoException(); 
				if(Integer.parseInt(cs.getNum_vacunas())<0)
					throw new NumeroMinimoException(); 
					
				center.save(cs);

				}
			
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
		return "Centro con id: "+cs.getId();
	}
	@GetMapping("/findAllCenters")
	public List<CentroSalud> getCentros(){
		return center.findAll();
	}
	@GetMapping("/findAllCenters/{id}")
	public Optional<CentroSalud> getCentro(@PathVariable String id){
		return center.findById(id);
	}
	/*@DeleteMapping("/delete/{id}")
	public String deleteUsuarios(@PathVariable String id) {
		user.deleteById(id);
		return "Usuario eliminado con id: "+id;
	}*/
	
	public static boolean esNumericoEntero(String cadena) throws ValorNumericoException{
           try{
        	   Integer.parseInt(cadena);
               return true;

           } catch (Exception e) {
        	   throw new ValorNumericoException();
           }
	}
}