package com.capo.redisVersion2.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.capo.redisVersion2.interfaces.UserService;

import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {
	
	@Override
	public Mono<UserDetails> findByUsername(String username) {
		return null;
	}

}
