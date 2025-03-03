package com.capo.redisVersion2.globalException;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ArrayIndexOutOfBoundsException.class)
	public Mono<ErrorResponse> arrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException exception) {
		return Mono.just(ErrorResponse.builder(exception, HttpStatus.CONFLICT,exception.getMessage()).build());
	}
	
	@ExceptionHandler(Exception.class)
	public Mono<ErrorResponse> arrayIndexOutOfBoundsException(Exception exception) {
		return Mono.just(ErrorResponse.builder(exception, HttpStatus.INTERNAL_SERVER_ERROR,exception.getMessage()).build());
	}
}
