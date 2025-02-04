package com.capo.redisVersion2.service;

import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Mono;

@SpringBootTest
public class CostImplementationTest {
	
	@Autowired
	CostImplementation costImplementation;
	
	@Autowired
	private RedissonReactiveClient client;
	
	@Test
	public void buildingGraphTest() throws Exception{
		
		costImplementation.buildingGraph();
	}
	
	@Test
	public void setGraphTest() throws Exception{
		RMapReactive<String,String> map= this.client.getMap("prueba",StringCodec.INSTANCE);
		Mono<String> edad= map.put("edad", "51");
		Map<String,String> result= map.entryIterator().collectMap(Map.Entry::getKey,Map.Entry::getValue).block();
		assert(Objects.nonNull(result));
	}
}
