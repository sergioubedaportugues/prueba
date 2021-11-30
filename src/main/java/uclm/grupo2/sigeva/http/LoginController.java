package uclm.grupo2.sigeva.http;



import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import uclm.grupo2.sigeva.dao.TokenDAO;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.exceptions.CredencialesInvalidasException;
import uclm.grupo2.sigeva.exceptions.TokenBorradoException;
import uclm.grupo2.sigeva.model.Usuario;
import uclm.grupo2.sigeva.model.UsuarioDTO;
import uclm.grupo2.sigeva.model.Token;


@RestController
@RequestMapping("login")
public class LoginController {

	@Autowired
	private TokenDAO token;

	@Autowired
	private UsuarioDAO user;
	
	@PostMapping("/iniciarSesion")
    public Usuario iniciarSesion(@RequestBody UsuarioDTO uDTO){
		Usuario usuarios = cambiarDTO(uDTO);
        try {
        	token.deleteAll();
            List <Usuario> optUser = user.getByLogin(usuarios.getLogin());
            if(!optUser.isEmpty()) {
            	Usuario usua = optUser.get(0);
                if(DigestUtils.sha512Hex(usuarios.getPassword()).equals(usua.getPassword()) || usuarios.getPassword().length() == 128) {
                	Usuario usuarioActual = usua;
                	Token tok = new Token();
                	tok.setLogin(usua.getLogin());
                	token.save(tok);
                	return usuarioActual;
                } else 
                	throw new CredencialesInvalidasException();
            } else
            	throw new CredencialesInvalidasException();
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
	@DeleteMapping("/cerrarSesion")
    public void cerrarSesion(){
        try {
        	List <Token> optToken = token.findAll();
        	if(!optToken.isEmpty())
        		token.delete(optToken.get(0));
        	else
            	throw new TokenBorradoException();
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
	
	private Usuario cambiarDTO(UsuarioDTO uDTO) {
		Usuario usu = new Usuario();
		usu.setId(uDTO.getId());
		usu.setLogin(uDTO.getLogin());
		usu.setPassword(uDTO.getPassword());
		usu.setNombre(uDTO.getNombre());
		usu.setApellidos(uDTO.getApellidos());
		usu.setTelefono(uDTO.getTelefono());
		usu.setDni(uDTO.getDni());
		usu.setRol(uDTO.getRol());
		usu.setCs(uDTO.getCs());
		usu.setDosis(uDTO.getDosis());
		return usu;
	}

	
	
}