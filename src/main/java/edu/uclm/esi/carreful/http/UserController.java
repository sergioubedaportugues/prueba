package edu.uclm.esi.carreful.http;

import java.io.IOException;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import edu.uclm.esi.carreful.dao.TokenDao;
import edu.uclm.esi.carreful.dao.UserDao;
import edu.uclm.esi.carreful.exceptions.CarrefulCamposVaciosException;
import edu.uclm.esi.carreful.exceptions.CarrefulPassMinimunLengthException;
import edu.uclm.esi.carreful.exceptions.CarrefulPassNotEqualException;
import edu.uclm.esi.carreful.model.User;
import edu.uclm.esi.carreful.tokens.Email;
import edu.uclm.esi.carreful.tokens.Token;


@RestController
@RequestMapping("user")
public class UserController extends CookiesController {
	
	@Autowired
	UserDao userDao;
	@Autowired
	TokenDao tokenDao;
	
	@GetMapping("usarToken/{tokenId}")
	public void usarToken(HttpServletResponse response, @PathVariable String tokenId) throws IOException {
		Optional<Token> optToken =tokenDao.findById(tokenId);
		if (optToken.isPresent()) {
			Token token = optToken.get();
			if(token.isUsed()) {
				response.sendError(409,"El token ya se utilizó");
			}else {
				response.sendRedirect("http://localhost?ojr=setNewPassword&token="+tokenId+"&email="+token.getEmail());
			}
			
		}else {
			response.sendError(404,"El token no existe");
		}	
	}
	
	@GetMapping("/recoverPwd")
	public void recoverPwd(@RequestParam String email) {
		try {
			User user = userDao.findByEmail(email);
			if (user!=null) {
				Token token = new Token(email);
				tokenDao.save(token);
				Email smtp = new Email();
				String texto = "Para recuperar tu contraseña, pulsa aquí:" + 
						"<a href='http://localhost/user/usarToken/" + token.getId() + "'>aquí</a>";
				smtp.send(email, "Carreful: recuperación de contraseña", texto);
				
			}
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
			}
		
	}
	
	//id email fechaCaducidad usado
	
	@PutMapping("/register")
	public void register(@RequestBody Map<String,Object> info) {
		try {
			//mapa con la informacion que ha viajado del json
			JSONObject jso = new JSONObject(info);
			String userName = jso.optString("userName"); //si esta vacia lanzar excepcion
			if (userName.length()==0)
				throw new CarrefulCamposVaciosException();
			String email = jso.optString("email");
			if (email.length()==0)
				throw new CarrefulCamposVaciosException();
			String pwd1 = jso.optString("pwd1");
			String pwd2 = jso.optString("pwd2");
			if(!pwd1.equals(pwd2))
				throw new CarrefulPassNotEqualException();
			if(pwd1.length()<8)
				throw new CarrefulPassMinimunLengthException();
			User user = new User();
			user.setEmail(email);
			user.setPwd(pwd1);
			user.setPicture(jso.optString("picture"));
			userDao.save(user);
			
			
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
	@PostMapping("/login")
	public void login(HttpServletRequest request, @RequestBody Map<String,Object> info) {
		try {
			//mapa con la informacion que ha viajado del json
			JSONObject jso = new JSONObject(info);
			String email = jso.optString("email"); //si esta vacia lanzar excepcion
			if (email.length()==0)
				throw new CarrefulCamposVaciosException();
			String pwd = jso.optString("pwd");
			User user = userDao.findByEmailAndPwd(email,DigestUtils.sha512Hex(pwd));
			if(user==null)
				System.out.println("El usuario no existe");
			request.getSession().setAttribute("userEmail", email);
			
			
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
	
	@Override
	@GetMapping("/comprobarCredenciales")
	public void comprobarCredenciales(HttpServletRequest request) {
		if (request.getSession().getAttribute("userEmail")==null)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
	}
}







