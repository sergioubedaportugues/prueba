package uclm.grupo2.sigeva.http;

import java.util.List;
import java.util.Optional;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.exceptions.CamposVaciosException;
import uclm.grupo2.sigeva.exceptions.FormatoDniException;
import uclm.grupo2.sigeva.exceptions.NoEsTelefonoException;
import uclm.grupo2.sigeva.exceptions.RolInvalidoException;
import uclm.grupo2.sigeva.exceptions.UsuarioDuplicadoException;

import uclm.grupo2.sigeva.model.Usuario;

@RestController
@RequestMapping("gestionUsuarios")
public class UsuarioController {

	@Autowired
	private UsuarioDAO user;
	
	@PostMapping("/insertUsers")
	public String insertarUsuario(@RequestBody Usuario usuarios) {
		try {
			Optional<Usuario> optUser = user.findByLogin(usuarios.getLogin());
			if (optUser.isPresent())
				throw new UsuarioDuplicadoException();
			else {
				if(usuarios.getLogin().isEmpty())
					throw new CamposVaciosException();
				if(usuarios.getPassword().isEmpty())
					throw new CamposVaciosException();
				if(usuarios.getNombre().isEmpty())
					throw new CamposVaciosException();
				if(usuarios.getApellidos().isEmpty())
					throw new CamposVaciosException();
				if(!validarMovil(usuarios.getTelefono()))
					throw new NoEsTelefonoException();
				if(!validarDni(usuarios.getDni())) 
					throw new FormatoDniException();
				if(usuarios.getRol().isEmpty())
					throw new CamposVaciosException();
				if(!((usuarios.getRol().equals("Sanitario")) || (usuarios.getRol().equals("Admin")) || (usuarios.getRol().equals("Paciente"))))
					throw new RolInvalidoException();
				usuarios.setDni(DigestUtils.sha512Hex(usuarios.getDni()));
				user.save(usuarios);
				}
			
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
		return "Usuario con id: "+usuarios.getId();
	}
	@GetMapping("/findAllUsers")
	public List<Usuario> getUsuarios(){
		return user.findAll();
	}
	@GetMapping("/findAllUsers/{id}")
	public Optional<Usuario> getUsuario(@PathVariable String id){
		return user.findById(id);
	}
	
	private static boolean validarMovil(String telefono) {
		if(telefono.length()!=9) {
			return false;
		}
		boolean isNumeric =  telefono.matches("[+-]?\\d*(\\.\\d+)?");
		if(!isNumeric)
			return isNumeric;
		
		return isNumeric;
	}

	private static boolean validarDni(String dni) {
		boolean valido = false;
		if(dni.length()!=9)
			return valido;
        for (int i = 0; i < dni.length()-1; i++) {
            if (!Character.isDigit(dni.charAt(i))) {
    			return valido;
            }
        }
        if(!Character.isAlphabetic(dni.charAt(8)))
        	return valido;
        valido = true;
        return valido;
	}
}
