package com.example.config.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class AuthenticationTokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationTokenProvider.class);

	@Value("${auth.secretKey}")
	private String authSecretKey;

	@Value("${auth.expirationInMs}")
	private int authExpirationInMs;

	public String generateToken(Integer id) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + authExpirationInMs);
		return Jwts.builder().setSubject(id.toString())
				.setIssuedAt(new Date()).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, authSecretKey)
				.compact();
	}

	public String getIdFromAuthToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(authSecretKey).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(authSecretKey).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid Auth signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid Auth token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired Auth token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported Auth token");
		} catch (IllegalArgumentException ex) {
			logger.error("Auth claims string is empty.");
		}
		return false;
	}

}
