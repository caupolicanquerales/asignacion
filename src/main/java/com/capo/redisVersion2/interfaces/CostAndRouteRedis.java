package com.capo.redisVersion2.interfaces;

import com.capo.redisVersion2.request.VertexRedisRequest;
import com.capo.redisVersion2.response.ResponseCostDestinations;
import com.capo.redisVersion2.response.ResponseGraphRedis;

import reactor.core.publisher.Mono;

public interface CostAndRouteRedis {
	Mono<String> buildingGraph();
	Mono<ResponseGraphRedis> estimationOfCosts(String vertex);
	Mono<String> saveAndUpdateCostAndDestination(VertexRedisRequest request);
	Mono<String> removeCostAndDestination(VertexRedisRequest request);
	String saveAndUpdateCostAndDestinationStartingApp(VertexRedisRequest request);
	Mono<ResponseCostDestinations> getAllCostsAndDestinations();
}
