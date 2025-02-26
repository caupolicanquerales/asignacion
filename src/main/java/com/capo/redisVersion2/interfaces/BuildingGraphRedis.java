package com.capo.redisVersion2.interfaces;

import com.capo.redisVersion2.dto.GraphObject;

import reactor.core.publisher.Mono;

public interface BuildingGraphRedis {
	Mono<GraphObject> buildingGraph();
}
