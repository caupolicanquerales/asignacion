package com.capo.redisVersion2;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLongReactive;
import org.redisson.api.RList;
import org.redisson.api.RListReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveValueOperations;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class RedisVersion2ApplicationTests {
	
	@Autowired
	private RedissonReactiveClient client;
	
	@Autowired
	private RedissonClient redissonClient;
	
	@Test
	void redissonTest() {
		RAtomicLongReactive atomicLong= this.client.getAtomicLong("stores:1:visit");
		Mono<Void> mono= Flux.range(1, 1000)
		.flatMap(i-> atomicLong.incrementAndGet()).then();
		StepVerifier.create(mono).verifyComplete();
	}
	
	@Test
	void redissonListTest() {
		RListReactive<String> list= this.client.getList("costos",StringCodec.INSTANCE);
		List<String> mono= list.iterator().collectList().block();
		assert(mono.size()>0);		
		
	}
	
	@Test
	void redissonMapTest() {
		RMapReactive<Integer,String> map= this.client.getMap("costos",StringCodec.INSTANCE);
		Map<Integer,String> mapPuntos= map.entryIterator().collectMap(Map.Entry::getKey,Map.Entry::getValue).block();
		assert(Objects.nonNull(mapPuntos));		
		
	}

}
