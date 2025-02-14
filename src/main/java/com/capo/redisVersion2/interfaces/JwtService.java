package com.capo.redisVersion2.interfaces;

import reactor.core.publisher.Mono;

//subject is userId in this app
public interface JwtService {
	Mono<Boolean> validateJwt(String token);
	String extractTokenSubject(String token);
}
