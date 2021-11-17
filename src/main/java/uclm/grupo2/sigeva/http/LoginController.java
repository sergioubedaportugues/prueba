package uclm.grupo2.sigeva.http;



import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import uclm.grupo2.sigeva.dao.TokenDAO;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.exceptions.CredencialesInvalidasException;
import uclm.grupo2.sigeva.model.Usuario;
import uclm.grupo2.sigeva.model.Token;


@RestController
@RequestMapping("login")
public class LoginController {

	@Autowired
	private TokenDAO token;

	@Autowired
	private UsuarioDAO user;
	
	@PostMapping("/iniciarSesion")
    public Usuario iniciarSesion(@RequestBody Usuario usuarios){
        try {
        	Usuario usuarioActual = new Usuario();
            List <Usuario> optUser = user.getByLogin(usuarios.getLogin());
            if(!optUser.isEmpty()) {
            	Usuario usua = optUser.get(0);
                if(DigestUtils.sha512Hex(usuarios.getPassword()).equals(usua.getPassword())) {
                	usuarioActual=usua;
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
}
