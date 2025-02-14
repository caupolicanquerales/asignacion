package com.capo.redisVersion2.config;

import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.capo.redisVersion2.interfaces.JwtService;

import io.jsonwebtoken.lang.Collections;
import reactor.core.publisher.Mono;

//Clase a colocar en los otros micros para realizar la autenticacion

public class JwtAuthenticationFilter implements WebFilter {
	
	private final String wordInHeader="Bearer ";
	private JwtService jwtService;
	
	public JwtAuthenticationFilter(JwtService jwtService) {
		this.jwtService= jwtService;
	}
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String token= extractToken(exchange);
		if(Objects.isNull(token)) {
			return chain.filter(exchange);
		}
		return validateToken(token)
				.flatMap(isValid-> isValid? authenticateAndContinue(token, exchange, chain):
					handleInvalidToken(exchange));
	}
	
	private Mono<Void> authenticateAndContinue(String token,ServerWebExchange exchange, WebFilterChain chain ){
		return Mono.just(jwtService.extractTokenSubject(token))
				.flatMap(subject->{
					Authentication auth= new UsernamePasswordAuthenticationToken(subject, null, Collections.emptyList());
					return chain.filter(exchange)
							.contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
				});
	}
	
	private Mono<Void> handleInvalidToken(ServerWebExchange exchange){
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		return exchange.getResponse().setComplete();
	}
	
	private String extractToken(ServerWebExchange exchange) {
		String authorizationHeader= exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if(StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(wordInHeader)) {
			return authorizationHeader.substring(wordInHeader.length()).trim();
		}
		return null;
	}
	
	private Mono<Boolean> validateToken(String token){
		return jwtService.validateJwt(token);
	}
}
