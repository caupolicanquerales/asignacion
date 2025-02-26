package com.capo.redisVersion2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthenticationManagerConfig {
	
	private final ReactiveUserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	
	public AuthenticationManagerConfig(ReactiveUserDetailsService userDetailsService,PasswordEncoder passwordEncoder){
		this.userDetailsService=userDetailsService;
		this.passwordEncoder= passwordEncoder;
	}
	
	@Bean
	public ReactiveAuthenticationManager reactiveAuthenticationManager() {
		UserDetailsRepositoryReactiveAuthenticationManager authenticationManager =
				new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
		authenticationManager.setPasswordEncoder(passwordEncoder);
		return authenticationManager;
	} 
}
