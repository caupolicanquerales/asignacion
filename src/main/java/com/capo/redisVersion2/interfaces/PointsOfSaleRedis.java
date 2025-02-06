package com.capo.redisVersion2.interfaces;

import com.capo.redisVersion2.request.RequestPointsRedis;
import com.capo.redisVersion2.response.ResponsePointsRedis;

import reactor.core.publisher.Mono;

public interface PointsOfSaleRedis {
	Mono<String> saveAndUpdateCostPointsOfSale(RequestPointsRedis request);
	Mono<String> removePointsOfSale(RequestPointsRedis request);
	Mono<ResponsePointsRedis> getPointsOfSale();
}
