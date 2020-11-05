package com.example.i_o_spring_project.tokens;

import java.io.IOException;
import java.util.Enumeration;

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
		System.err.println("doFilterInternal");

//		System.err.println("request: "+request.getMethod());
//		request.getHeaderNames();

		Enumeration<String> headerNames = request.getHeaderNames();
//
//		    if (headerNames != null) {
//		            while (headerNames.hasMoreElements()) {
//		                    System.out.println("Header: " + request.getHeader(headerNames.nextElement()));
//		            }
//		    }

		if (request.getMethod().equals("OPTIONS")) {
//			 System.err.println("*******options*******");
			response.setStatus(HttpServletResponse.SC_OK);
			filterChain.doFilter(request, response);
			return;
		}
		String authorization = request.getHeader("authorization");

		System.err.println("authorization: " + authorization);

//		System.err.println("checkAuthorization: " + checkAuthorization(authorization, response));
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
		System.err.println("token: " + token);
		try {
			return tokenService.parseToken(token);
		} catch (ExpiredJwtException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token has expired");
			return null;
		} catch (UnsupportedJwtException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token unsupported");
			return null;
		} catch (MalformedJwtException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "the token was not constructed correctly!");
			return null;
		} catch (SignatureException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token signature is not valid");
			return null;
		}
	}

	public boolean checkAuthorization(String authorization, HttpServletResponse response) throws IOException {
		if (authorization == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token not found");
			System.err.println("we fail here");
			return true;
		}
		if (!authorization.startsWith("Bearer")) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token does not start with 'Bearer'");
			return true;
		}
		return false;
//		return authorization == null || !autorization.startsWith("Bearer");
	}

}
