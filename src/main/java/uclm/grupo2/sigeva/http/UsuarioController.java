package uclm.grupo2.sigeva.http;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.model.Usuario;

@RestController
public class UsuarioController {

	@Autowired
	private UsuarioDAO user;
	
	@PostMapping("/insertUsers")
	public String insertarUsuario(@RequestBody Usuario usuarios) {
		user.save(usuarios);
		return "Usuario con id: "+usuarios.getId();
	}
	@GetMapping("/findAllUsers")
	public List<Usuario> getUsuarios(){
		return user.findAll();
	}
	@GetMapping("/findAllUsers/{id}")
	public Optional<Usuario> getUsuario(@PathVariable int id){
		return user.findById(id);
	}
	@DeleteMapping("/delete/{id}")
	public String deleteUsuarios(@PathVariable int id) {
		user.deleteById(id);
		return "Usuario eliminado con id: "+id;
	}
	
}
