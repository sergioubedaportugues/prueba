package edu.uclm.esi.carreful.http;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

public abstract class CookiesController {
	public static final String COOKIE_NAME = "laCookie";
	public static final String COOKIE_PATH = "/";
	
	@GetMapping("/comprobarCredenciales")
	public void comprobarCredenciales(HttpServletRequest request) {
		if (request.getSession().getAttribute("userEmail")==null)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
	}

	protected Cookie readOrCreateCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies==null)
			return createCookie(response);
		Cookie cookie = findCookie(cookies);
		if (cookie==null)
			cookie = createCookie(response);
		return cookie;
	}

	private Cookie findCookie(Cookie[] cookies) {
		for (Cookie cookie : cookies)
			if (cookie.getName().equals(COOKIE_NAME))
				return cookie;
		return null;
	}

	private Cookie createCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(COOKIE_NAME, UUID.randomUUID().toString());
		cookie.setPath(COOKIE_PATH);
		cookie.setMaxAge(30*24*60*60);
		response.addCookie(cookie);
		return cookie;
	}
}
