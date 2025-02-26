package com.capo.redisVersion2.interfaces;

import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;

import com.capo.redisVersion2.dto.GraphObject;

import reactor.core.publisher.Mono;

public interface BasicPetitionRedis {
	RMapReactive<String,String> getReactiveMap(String mapName);
	Mono<GraphObject> getReactiveJsonBucket(String jsonName);
	String saveReactiveJsonBucket(String jsonName, GraphObject graph);
	RMapCacheReactive <String,String> getRMapCahe(String mapName);
}
