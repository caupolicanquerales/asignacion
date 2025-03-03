package com.capo.redisVersion2.interfaces;

import com.capo.redisVersion2.request.AccreditationRequest;

import reactor.core.publisher.Mono;

public interface Accreditation {
	Mono<String> createAccreditation(AccreditationRequest request);
}
