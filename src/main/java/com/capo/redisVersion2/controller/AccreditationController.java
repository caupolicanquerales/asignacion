package com.capo.redisVersion2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capo.redisVersion2.interfaces.Accreditation;
import com.capo.redisVersion2.request.AccreditationRequest;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("accreditation")
public class AccreditationController {
	
	@Autowired
	Accreditation accreditation;
	
	@PostMapping("/create")
	public Mono<ResponseEntity<String>> createAccreditation(@RequestBody AccreditationRequest request){
		return accreditation.createAccreditation(request)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
}
