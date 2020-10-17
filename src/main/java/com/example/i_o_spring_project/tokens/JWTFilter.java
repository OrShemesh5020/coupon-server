package com.example.i_o_spring_project.tokens;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.i_o_spring_project.model.UserPrincipal;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTFilter extends OncePerRequestFilter {

	@Autowired
	protected TokenServiceImpl tokenService;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorization = request.getHeader("authorization");

		if (checkAuthorization(authorization, response)) {
			return;
		}

		UserPrincipal userPrincipal = validateToken(authorization, response);
		if (userPrincipal == null) {
			return;
		}

		request.setAttribute("id", userPrincipal.getId());
		request.setAttribute("type", userPrincipal.getClientType().name().toLowerCase());

		filterChain.doFilter(request, response);
	}

	private UserPrincipal validateToken(String autorization, HttpServletResponse response) throws IOException {
		String token = autorization.split(" ")[1];

		try {
			return tokenService.parseToken(token);
		} catch (ExpiredJwtException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "token has expired");
			return null;
		} catch (UnsupportedJwtException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "token unsupported");
			return null;
		} catch (MalformedJwtException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "the token was not constructed correctly!");
			return null;
		} catch (SignatureException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "token signature is not valid");
			return null;
		}
	}

	public boolean checkAuthorization(String authorization, HttpServletResponse response) throws IOException {
		if (authorization == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "token not found");
			return true;
		}
		if (!authorization.startsWith("Bearer")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "token does not start with 'Bearer'");
			return true;
		}
		return false;
//		return authorization == null || !autorization.startsWith("Bearer");
	}

}
