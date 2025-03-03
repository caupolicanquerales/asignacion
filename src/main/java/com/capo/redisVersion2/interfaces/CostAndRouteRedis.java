package com.capo.redisVersion2.interfaces;

import com.capo.redisVersion2.request.VertexRedisRequest;
import com.capo.redisVersion2.response.ResponseCostDestinations;
import com.capo.redisVersion2.response.ResponseGraphRedis;

import reactor.core.publisher.Mono;

public interface CostAndRouteRedis {
	Mono<String> buildingGraph();
	Mono<ResponseGraphRedis> estimationOfCosts(String vertex);
	Mono<String> updateCost(VertexRedisRequest request);
	Mono<String> deleteCostAndDestination(VertexRedisRequest request);
	String saveAndUpdateCostAndDestinationStartingApp(VertexRedisRequest request);
	Mono<ResponseCostDestinations> getAllCostsAndDestinations();
	Mono<String> saveCostAndDestination(VertexRedisRequest request);
}
