package br.com.turbomotors.turbomotors.Servico;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieService {
	
	public static void setCookie(HttpServletResponse response, String Key, String Valor, int Segundos) {
		Cookie jwtSecurityCookie = new Cookie(Key, Valor);
		jwtSecurityCookie.setMaxAge(Segundos);
		jwtSecurityCookie.setPath("/");
		jwtSecurityCookie.setHttpOnly(true);
		response.addCookie(jwtSecurityCookie);
		
	}
	
	public static String getCookie(HttpServletRequest request, String key) {
		return Optional.ofNullable(request.getCookies())
				.flatMap(cookies -> Arrays.stream(cookies)
						.filter(jwtSecurityCookie -> key.equals(jwtSecurityCookie.getName()))
				.findAny()
				).map(e -> e.getValue())
				.orElse(null);
		
	}
	
}
