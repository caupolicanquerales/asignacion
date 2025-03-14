package com.capo.redisVersion2.interfaces;

import com.capo.redisVersion2.request.PointsRedisRequest;
import com.capo.redisVersion2.response.ResponsePointsRedis;

import reactor.core.publisher.Mono;

public interface PointsOfSaleRedis {
	Mono<String> updateCostPointsOfSale(PointsRedisRequest request);
	Mono<String> removePointsOfSale(PointsRedisRequest request);
	Mono<ResponsePointsRedis> getPointsOfSale();
	String savePointsOfSaleStartingApp(PointsRedisRequest request);
	Mono<String> savePointsOfSale(PointsRedisRequest request);
}
