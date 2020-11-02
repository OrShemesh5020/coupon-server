package com.example.i_o_spring_project.tokens;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.i_o_spring_project.model.ClientType;
import com.example.i_o_spring_project.model.User;
import com.example.i_o_spring_project.model.UserPrincipal;
import com.example.i_o_spring_project.service.TokensService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenServiceImpl implements TokensService {


	private Date expirationDate;
	
	@Value("${key}")
	private String key;

	@Override
	public String createJWT(User user) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		JwtBuilder tokenBuilder = Jwts.builder().claim("id", user.getId()).setSubject(user.getEmail())
				.claim("clientType", user.getClientType()).claim("password", user.getPassword()).setIssuedAt(now)
				.signWith(signatureAlgorithm, key);
		long expMillis = nowMillis + 1800000;
		Date exp = new Date(expMillis);
		setExpirationDate(exp);
		tokenBuilder.setExpiration(exp);
		return tokenBuilder.compact();
	}

	
	@Override
	public UserPrincipal parseToken(String token) {
		
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
//		String username = claims.getSubject();
		Integer userId = claims.get("id", Integer.class);
//		String password = claims.get("password", String.class);
		String type = claims.get("clientType", String.class);
//		Date expirationDate = claims.getExpiration();
		ClientType userType = getClientType(type);
		return new UserPrincipal(userId, userType);
	}

	private ClientType getClientType(String type) {
		switch (type.toLowerCase()) {
		case "administrator":
			return ClientType.ADMINISTRATOR;
		case "company":
			return ClientType.COMPANY;
		default:
			return ClientType.CUSTOMER;
		}
	}

	private void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

}
