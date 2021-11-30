package uclm.grupo2.sigeva.http;

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
import uclm.grupo2.sigeva.exceptions.CamposVaciosException;
import uclm.grupo2.sigeva.exceptions.CentroConCitasException;
import uclm.grupo2.sigeva.exceptions.CentroDuplicadoException;
import uclm.grupo2.sigeva.exceptions.CentroInexistenteException;
import uclm.grupo2.sigeva.exceptions.NumeroMinimoException;
import uclm.grupo2.sigeva.exceptions.TokenBorradoException;
import uclm.grupo2.sigeva.exceptions.ValorNumericoException;


import uclm.grupo2.sigeva.exceptions.FormatoHoraException;
import uclm.grupo2.sigeva.model.CentroSalud;
import uclm.grupo2.sigeva.model.CentroSaludDTO;
import uclm.grupo2.sigeva.model.Citas;
import uclm.grupo2.sigeva.model.Usuario;

@RestController
@RequestMapping("gestionCentroSalud")
public class CentroSaludController {

	@Autowired
	private CentroSaludDAO center;
	
	@Autowired
	private CitasDAO cita;
	
	@Autowired TokenDAO tokenLogin;
	
	@Autowired UsuarioDAO user;

	@PostMapping("/insertCenter")
	public String insertarCentro(@RequestBody CentroSaludDTO csDTO) {
		CentroSalud cs = cambiarCentroDTO(csDTO);
		

		try {
			validarLogin();
			List<CentroSalud> optCenter = center.findByNombre(cs.getNombre());
			if (!optCenter.isEmpty())
				throw new CentroDuplicadoException();
			else {
				if(cs.getNombre().isEmpty()||cs.getDireccion().isEmpty() || cs.getNumVacunas().isEmpty() ||cs.getfInicio().isEmpty() || cs.getfFin().isEmpty()   || cs.getCupo().isEmpty())
					throw new CamposVaciosException();
				if(!esNumericoEntero(cs.getNumVacunas()) || !esNumericoEntero(cs.getCupo()) || !esNumericoEntero(cs.getFranja()))
					throw new ValorNumericoException(); 
				if(Integer.parseInt(cs.getNumVacunas())<0 || Integer.parseInt(cs.getCupo())<0)
					throw new NumeroMinimoException();
				if((!validarHoras(cs.getfInicio()) || !tiempoHoras(cs.getfInicio()) || !validarHoras(cs.getfFin()) || !tiempoHoras(cs.getfFin())) || (!controlHoras(cs.getfInicio(), cs.getfFin()))  )
					throw new FormatoHoraException();
				center.save(cs);
				}
			
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
		return "Centro con id: "+cs.getId();
	}

	@GetMapping("/findAllCenters")
	public List<CentroSalud> getCentros() throws TokenBorradoException{
		validarLogin();
		return center.findAll();
	}

	@DeleteMapping("/deleteCenter")
	public String borrarCentro(@RequestBody CentroSaludDTO csDTO) {
		CentroSalud cs = cambiarCentroDTO(csDTO);

		try {
			validarLogin();
			Optional<CentroSalud> optCenter = center.findById(cs.getId());
			if (optCenter.isPresent()) {
				if(cita.findByCs(cs).isEmpty()) {
					center.deleteById(cs.getId());
				} else
					throw new CentroConCitasException();
			} else
				throw new CentroInexistenteException();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
		return "Centro eliminado";
	}
	
	@PostMapping("/modifyCenter")
	public String modificarCentro(@RequestBody CentroSaludDTO csDTO) {
		CentroSalud cs = cambiarCentroDTO(csDTO);
		
		try {
			validarLogin();
			Optional<CentroSalud> optCenter = center.findById(cs.getId());
			if (optCenter.isPresent()) {
				List <Usuario> cambiarCsUsu = user.getByCs(optCenter.get());
				List <Citas> cambiarCsCitas = cita.findByCs(optCenter.get());
				CentroSalud preCentro = optCenter.get();
				 	if(cs.getNombre().isEmpty() || cs.getDireccion().isEmpty() || cs.getNumVacunas().isEmpty())
				 		throw new CamposVaciosException();
				 	if(!esNumericoEntero(cs.getNumVacunas()) || Integer.parseInt(cs.getNumVacunas())<0)
				 		throw new ValorNumericoException();
				 	preCentro.setNombre(cs.getNombre());
				 	preCentro.setDireccion(cs.getDireccion());
				 	preCentro.setNumVacunas(cs.getNumVacunas());
				 	
					 // CAMBIARLO POR CENTROS
					for (int i=0; i<cambiarCsUsu.size();i++) {
						cambiarCsUsu.get(i).setCs(preCentro);
						user.save(cambiarCsUsu.get(i));
					}
					
					 // CAMBIARLO POR CENTROS
					for (int i=0; i<cambiarCsCitas.size();i++) {
						cambiarCsCitas.get(i).getPaciente().setCs(preCentro);
						cambiarCsCitas.get(i).setCs(preCentro);
						cita.save(cambiarCsCitas.get(i));
					}
				 	
	                center.save(preCentro);			
			}
			else
				throw new CentroInexistenteException();
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
		return "Centro modificado";
	}
	
	
	public static boolean esNumericoEntero(String cadena) throws ValorNumericoException{
           try{
        	   boolean isNumeric =  cadena.matches("[+-]?\\d*(\\.\\d+)?");
        	   if(!isNumeric)
        		   return isNumeric;
               return isNumeric;
           } catch (Exception e) {
        	   throw new ValorNumericoException();
           }
	}

			
	private static boolean validarHoras(String hora) {
		if (hora.length() != 5)
			return false;
		for (int i = 0; i < hora.length() - 1; i++) {
			if (i == 2) {
				if (hora.charAt(i) != ':')
					return false;
			} else {
				if (!Character.isDigit(hora.charAt(i))) {
					return false;
				}
			}
		}
		return true;
	}
	

	private static boolean controlHoras(String hInicio, String hFin) {
		String[] horaI = hInicio.split(":");
		String[] horaF = hFin.split(":");
		if (Integer.parseInt(horaI[0]) < Integer.parseInt(horaF[0])) {
			return true;
		} else {
			if (Integer.parseInt(horaI[0]) == Integer.parseInt(horaF[0]) && (Integer.parseInt(horaI[1]) < Integer.parseInt(horaF[1]))) {
				return true;
			}
		}
		return false;

	}
	private static boolean tiempoHoras(String horas) {
		boolean valido = false;
		String[] h = horas.split(":");
		if(Integer.parseInt(h[0])<24 && (Integer.parseInt(h[1])<60)) {
			valido = true;
		}
		return valido;
	}
	
	private void validarLogin() throws TokenBorradoException {
		if(tokenLogin.findAll().isEmpty())
			throw new TokenBorradoException();

    	List<Usuario> usuarios = user.getByLogin(tokenLogin.findAll().get(0).getLogin());
    	Usuario usu = usuarios.get(0);
        if(!usu.getRol().equals("Administrador"))
            throw new TokenBorradoException();
        }
	private CentroSalud cambiarCentroDTO(CentroSaludDTO csDTO) {
		CentroSalud cs = new CentroSalud();
		cs.setId(csDTO.getId());
		cs.setNombre(csDTO.getNombre());
		cs.setDireccion(csDTO.getDireccion());
		cs.setNumVacunas(csDTO.getNumVacunas());
		cs.setfInicio(csDTO.getfInicio());
		cs.setfFin(csDTO.getfFin());
		cs.setFranja(csDTO.getFranja());
		cs.setCupo(csDTO.getCupo());
		return cs;
	}
}