package com.capo.redisVersion2.interfaces;

import com.capo.redisVersion2.request.RequestVertexRedis;
import com.capo.redisVersion2.response.ResponseGraphRedis;

import reactor.core.publisher.Mono;

public interface CostAndRouteRedis {
	Mono<String> buildingGraph();
	Mono<ResponseGraphRedis> estimationOfCosts(String vertex);
	Mono<String> saveAndUpdateCostAndDestination(RequestVertexRedis request);
	Mono<String> removeCostAndDestination(RequestVertexRedis request);
}
