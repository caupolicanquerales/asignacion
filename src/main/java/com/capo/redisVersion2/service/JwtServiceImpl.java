package com.capo.redisVersion2.service;

import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.capo.redisVersion2.interfaces.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Service
public class JwtServiceImpl implements JwtService {

	@Autowired
	private Environment environment; 
	
	//colocar este metoddo en los otros micros que requieran usar JWT from request
	@Override
	public Mono<Boolean> validateJwt(String token) {
		return Mono.just(token)
				.map(this::parseToken)
				.map(claims->!claims.getExpiration().before(new Date()))
				.onErrorReturn(false);
	}
	
	@Override
	public String extractTokenSubject(String token) {
		return parseToken(token).getSubject();
	}
	
	private Claims parseToken(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	private SecretKey getSigningKey() {
		return Optional.ofNullable(environment.getProperty("token.secret"))
				.map(tokenSecret-> tokenSecret.getBytes())
				.map(tokenSecretBytes-> Keys.hmacShaKeyFor(tokenSecretBytes))
				.orElseThrow(()-> new IllegalArgumentException("token.secret must be in application.properties"));
	}
}
