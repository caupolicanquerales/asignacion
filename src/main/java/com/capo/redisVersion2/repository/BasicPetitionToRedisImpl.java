package com.capo.redisVersion2.repository;
import org.redisson.api.RJsonBucketReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.JacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capo.redisVersion2.dto.GraphObject;
import com.capo.redisVersion2.interfaces.BasicPetitionRedis;

import reactor.core.publisher.Mono;

@Repository
public class BasicPetitionToRedisImpl implements BasicPetitionRedis{
	
	@Autowired
	private RedissonReactiveClient client;
	
	
	@Override
	public RMapReactive<String,String> getReactiveMap(String mapName){
		return client.getMap(mapName,StringCodec.INSTANCE);
	}
	
	@Override
	public Mono<GraphObject> getReactiveJsonBucket(String jsonName){
		RJsonBucketReactive<GraphObject> json= client.getJsonBucket(jsonName,new JacksonCodec<>(GraphObject.class));
		return json.get();
	}
	
	@Override
	public String saveReactiveJsonBucket(String jsonName, GraphObject graph){
		RJsonBucketReactive<GraphObject> json= client.getJsonBucket(jsonName,new JacksonCodec<>(GraphObject.class));
		json.set(graph).then().subscribe();
		return "OK";
	}
}
