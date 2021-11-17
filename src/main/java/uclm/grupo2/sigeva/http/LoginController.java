package uclm.grupo2.sigeva.http;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import uclm.grupo2.sigeva.dao.UsuarioDAO;


@RestController
@RequestMapping("login")
public class LoginController {

	@Autowired
	private UsuarioDAO user;
	
	
	
}
