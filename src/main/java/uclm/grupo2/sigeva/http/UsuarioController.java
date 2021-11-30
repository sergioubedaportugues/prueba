package uclm.grupo2.sigeva.http;

import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import uclm.grupo2.sigeva.dao.CitasDAO;
import uclm.grupo2.sigeva.dao.TokenDAO;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.exceptions.CamposVaciosException;
import uclm.grupo2.sigeva.exceptions.EsUnAdministradorException;
import uclm.grupo2.sigeva.exceptions.FormatoDniException;
import uclm.grupo2.sigeva.exceptions.FormatoPasswordException;
import uclm.grupo2.sigeva.exceptions.NoEsTelefonoException;
import uclm.grupo2.sigeva.exceptions.PacienteConCitasException;
import uclm.grupo2.sigeva.exceptions.TokenBorradoException;
import uclm.grupo2.sigeva.exceptions.UsuarioConVacunaException;
import uclm.grupo2.sigeva.exceptions.UsuarioDuplicadoException;
import uclm.grupo2.sigeva.exceptions.UsuarioInexistenteException;
import uclm.grupo2.sigeva.model.Citas;
import uclm.grupo2.sigeva.model.Usuario;
import uclm.grupo2.sigeva.model.UsuarioDTO;

@RestController
@RequestMapping("gestionUsuarios")
public class UsuarioController {

	@Autowired
	private UsuarioDAO user;
	
	@Autowired
	private TokenDAO tokenLogin;
	
	@Autowired
	private CitasDAO cita;
		
	@PostMapping("/insertUsers")
	public String insertarUsuario(@RequestBody UsuarioDTO uDTO) {
		Usuario usuarios = cambiarUsuarioDTO(uDTO);
		try {
			validarLogin();
			Optional<Usuario> optUser = user.findByLogin(usuarios.getLogin());
			if (optUser.isPresent())
				throw new UsuarioDuplicadoException();
			else {
				if(usuarios.getLogin().isEmpty() || usuarios.getPassword().isEmpty()||usuarios.getNombre().isEmpty()||usuarios.getApellidos().isEmpty())
					throw new CamposVaciosException();
				if(!validarMovil(usuarios.getTelefono()))
					throw new NoEsTelefonoException();
				if(!validarPassword(usuarios.getPassword()))
					throw new FormatoPasswordException();
				if(!validarDni(usuarios.getDni())) 
					throw new FormatoDniException();
				usuarios.setPassword(DigestUtils.sha512Hex(usuarios.getPassword()));
				user.save(usuarios);
				
				}
			
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
		return "Usuario con id: "+usuarios.getId();
	}
	@GetMapping("/findAllUsers")
	public List<Usuario> getUsuarios() throws TokenBorradoException{
		validarLogin();
		return user.findAll();
	}
	
	@DeleteMapping("/deleteUser")
	public String borrarUsuario(@RequestBody UsuarioDTO uDTO) {
		Usuario usuario = cambiarUsuarioDTO(uDTO);
		try {
			validarLogin();
			Optional<Usuario> optUser = user.findById(usuario.getId());
			if (optUser.isPresent()) {
				if(!optUser.get().getRol().equals("Administrador")) {
					if(optUser.get().getDosis()!=1) {
						user.deleteById(usuario.getId());
						cita.deleteAll(cita.getByPacienteOrderByNumCitaAsc(optUser.get()));
					} else
						throw new UsuarioConVacunaException();
				} else 
					throw new EsUnAdministradorException();
			}else
				throw new UsuarioInexistenteException();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
		return "Usuario eliminado";
	}
	
	@PostMapping("/modifyUser")
	public String modificarUsuario(@RequestBody UsuarioDTO uDTO) {
		Usuario usuario = cambiarUsuarioDTO(uDTO);
		
		try {			
			validarLogin();			
			Optional<Usuario> optUser = user.findById(usuario.getId());
			
			if (optUser.isPresent()) {
					List <Citas> cambiarUsuCitas = cita.getByPacienteOrderByNumCitaAsc(optUser.get());
				 	Usuario preUsuario = optUser.get();
				 	preUsuario.setPassword(usuario.getPassword());
				 	comprobarCamposVacios(usuario);
				 	preUsuario.setNombre(usuario.getNombre());
				 	preUsuario.setApellidos(usuario.getApellidos());
				 	preUsuario.setTelefono(usuario.getTelefono());
				 	preUsuario.setDni(usuario.getDni());
				 	preUsuario.setRol(usuario.getRol());
				 	preUsuario=cambiarCentro(usuario, preUsuario);
					if(!validarMovil(preUsuario.getTelefono()))
						throw new NoEsTelefonoException();
					
					if(preUsuario.getPassword().length()!=128) {
						if(!validarPassword(preUsuario.getPassword())) {
							throw new FormatoPasswordException();
						}
						preUsuario.setPassword(DigestUtils.sha512Hex(preUsuario.getPassword()));
					}
					if(!validarDni(preUsuario.getDni())) 
						throw new FormatoDniException();
					
					for (int i=0; i<cambiarUsuCitas.size();i++) {
						cambiarUsuCitas.get(i).setPaciente(preUsuario);
						cita.save(cambiarUsuCitas.get(i));
					}
					
				 	user.save(preUsuario);	
			}
			else
				throw new UsuarioInexistenteException();
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
		return "Usuario modificado";
	}

	private Usuario cambiarCentro(Usuario usuario, Usuario preUsuario) throws PacienteConCitasException {
		if(usuario.getCs()==null) {
	 		preUsuario.setCs(preUsuario.getCs());
		}else {
			if(cita.getByPacienteOrderByNumCitaAsc(preUsuario).isEmpty())
				preUsuario.setCs(usuario.getCs());
			else
				throw new PacienteConCitasException();
		}
 		return preUsuario;

		
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
	
	private static boolean validarPassword(String pwd) {
		boolean mayus = false;
		boolean minus = false;
		boolean numero = false;
		boolean correcto = false;
		if(pwd.length()<8 || pwd.length()>=32)
			return false;
		
		for (int i = 0; i < pwd.length(); i++) {
            if (Character.isDigit(pwd.charAt(i))) {
    			numero=true;
            }
            if (Character.isLowerCase(pwd.charAt(i))) {
    			minus=true;
            }
            if (Character.isUpperCase(pwd.charAt(i))) {
    			mayus=true;
            }
        }
		if (numero && minus && mayus) {
			correcto=true;
		}
		return correcto;
	}
	
	private void comprobarCamposVacios(Usuario usuario) throws CamposVaciosException {
	if(usuario.getNombre().isEmpty() || usuario.getApellidos().isEmpty())
 		throw new CamposVaciosException();
	}
	private void validarLogin() throws TokenBorradoException {
		if(tokenLogin.findAll().isEmpty())
			throw new TokenBorradoException();

    	List<Usuario> usuarios = user.getByLogin(tokenLogin.findAll().get(0).getLogin());
    	Usuario usu = usuarios.get(0);
        if(!usu.getRol().equals("Administrador"))
            throw new TokenBorradoException();
        }
	private Usuario cambiarUsuarioDTO(UsuarioDTO uDTO) {
		Usuario usuarios = new Usuario();
		usuarios.setId(uDTO.getId());
		usuarios.setLogin(uDTO.getLogin());
		usuarios.setPassword(uDTO.getPassword());
		usuarios.setNombre(uDTO.getNombre());
		usuarios.setApellidos(uDTO.getApellidos());
		usuarios.setTelefono(uDTO.getTelefono());
		usuarios.setDni(uDTO.getDni());
		usuarios.setRol(uDTO.getRol());
		usuarios.setCs(uDTO.getCs());
		usuarios.setDosis(uDTO.getDosis());
		return usuarios;
	}
	
}
