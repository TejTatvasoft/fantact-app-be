package com.example.demo.security;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtTokenProvider {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.jwtExpirationMs}")
	private int jwtExpirationInMs;
		
	public String getUsername(String jwtToken) {
		Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
        String username = claims.getSubject();
        return username;
	}
	
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecret) ;
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String generateToken(Authentication authentication) {
	     
		String username = authentication.getName(); 
		Date currentDate = new Date();
	    Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

	    String token = Jwts.builder()
	    		.setHeaderParam("typ","JWT")
	    		.setSubject(username)
	            .claim("role", authentication.getAuthorities().toString())
	            .setIssuedAt(new Date())
	            .setExpiration(expireDate)
	            .signWith(getSignInKey())
	            .compact();
	    	return token;
	}
	
	public boolean validateToken(String jwtToken) {
		 
		try{
			Jwts.parserBuilder()
	        	.setSigningKey(getSignInKey())
	            .build()
	            .parse(jwtToken);
	        return true;
	    }catch (SignatureException ex) {
	        LOGGER.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			LOGGER.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			LOGGER.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			LOGGER.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			LOGGER.error("JWT claims string is empty.");
		}
		return false;
	}

}
