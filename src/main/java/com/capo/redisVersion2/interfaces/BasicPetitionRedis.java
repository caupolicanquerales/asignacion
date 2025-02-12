package com.capo.redisVersion2.interfaces;

import org.redisson.api.RJsonBucketReactive;
import org.redisson.api.RMapReactive;

import com.capo.redisVersion2.dto.GraphObject;

public interface BasicPetitionRedis {
	RMapReactive<String,String> getReactiveMap(String mapName);
	RJsonBucketReactive<GraphObject> getReactiveJsonBucket(String jsonName);
}
