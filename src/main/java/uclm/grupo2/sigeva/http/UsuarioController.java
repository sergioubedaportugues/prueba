package uclm.grupo2.sigeva.http;

import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
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

import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.exceptions.CamposVaciosException;
import uclm.grupo2.sigeva.exceptions.CredencialesInvalidasException;
import uclm.grupo2.sigeva.exceptions.FormatoDniException;
import uclm.grupo2.sigeva.exceptions.FormatoPasswordException;
import uclm.grupo2.sigeva.exceptions.NoEsTelefonoException;
import uclm.grupo2.sigeva.exceptions.UsuarioDuplicadoException;
import uclm.grupo2.sigeva.exceptions.UsuarioInexistenteException;
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
	public List<Usuario> getUsuarios(){
		return user.findAll();
	}
	@GetMapping("/findAllUsers/{id}")
	public Optional<Usuario> getUsuario(@PathVariable String id){
		return user.findById(id);
	}
	
	@DeleteMapping("/deleteUser")
	public String borrarUsuario(@RequestBody Usuario usuario) {
		try {
			Optional<Usuario> optUser = user.findById(usuario.getId());
			if (optUser.isPresent())
				user.deleteById(usuario.getId());

			else
				throw new UsuarioInexistenteException();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
		return "Usuario eliminado";
	}
	
	@PostMapping("/modifyUser")
	public String modificarUsuario(@RequestBody Usuario usuario) {
		try {
			
			Optional<Usuario> optUser = user.findById(usuario.getId());
			
			if (optUser.isPresent()) {
				 	Usuario preUsuario = optUser.get();
				 	preUsuario.setPassword(usuario.getPassword());
				 	preUsuario.setNombre(usuario.getNombre());
				 	preUsuario.setApellidos(usuario.getApellidos());
				 	preUsuario.setTelefono(usuario.getTelefono());
				 	preUsuario.setDni(usuario.getDni());
				 	preUsuario.setRol(usuario.getRol());
				 	if(usuario.getCs()==null) {
				 		preUsuario.setCs(preUsuario.getCs());
					}else {
				 	preUsuario.setCs(usuario.getCs());
					}
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
					
				 	user.save(preUsuario);			
			}
			else
				throw new UsuarioInexistenteException();
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
		return "Usuario modificado";
	}
	
	
	@PostMapping("/iniciarSesion")
	public Usuario iniciarSesion(@RequestBody String login, String password){
		try {
			Optional<Usuario> optUser = user.findByLogin(login);
			if(optUser.isPresent()) {
				Usuario usua = optUser.get();
				if(DigestUtils.sha512Hex(password).equals(usua.getPassword())) {
					System.out.print("holaa");
					return usua;
				} else {
					System.out.print("holaa");
					throw new CredencialesInvalidasException();
				}
			
			}
			return null;	
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
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
}
