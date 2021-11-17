package uclm.grupo2.sigeva.http;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
=======


import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
>>>>>>> refs/remotes/origin/Christopher
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
<<<<<<< HEAD
=======
import org.springframework.web.server.ResponseStatusException;

>>>>>>> refs/remotes/origin/Christopher
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.exceptions.CredencialesInvalidasException;
import uclm.grupo2.sigeva.model.Usuario;



@RestController
@RequestMapping("login")
public class LoginController {

	private Usuario usuarioActual = null;

	@Autowired
	private UsuarioDAO user;
	
<<<<<<< HEAD

	
=======
	@PostMapping("/iniciarSesion")
    public Usuario iniciarSesion(@RequestBody Usuario usuarios){
        try {
            List <Usuario> optUser = user.getByLogin(usuarios.getLogin());
            if(!optUser.isEmpty()) {
            	Usuario usua = optUser.get(0);
                if(DigestUtils.sha512Hex(usuarios.getPassword()).equals(usua.getPassword())) {
                	usuarioActual=usua;
                	return usuarioActual;
                } else 
                	throw new CredencialesInvalidasException();
            } else
            	throw new CredencialesInvalidasException();
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
>>>>>>> refs/remotes/origin/Christopher
	
}
