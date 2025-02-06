package com.capo.redisVersion2.service;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.api.RJsonBucketReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.JacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capo.redisVersion2.dto.GraphObject;
import com.capo.redisVersion2.response.ResponseGraphRedis;

import reactor.core.publisher.Mono;

@SpringBootTest
public class CostImplementationTest {
	
	@Autowired
	CostRedisImplementation costImplementation;
	
	@Autowired
	private RedissonReactiveClient client;
	
	Object uu= null;
	
	@Test
	public void buildingGraphTest() throws Exception{
		
		costImplementation.buildingGraph();
	}
	
	@Test
	public void estimationOfCostsTest() throws Exception{
		Mono<ResponseGraphRedis> result= costImplementation.estimationOfCosts("4");
		assert(Objects.nonNull(result));
	}
	
	@Test
	public void setGraphTest() throws Exception{
		RJsonBucketReactive<GraphObject> json =  client.getJsonBucket("graph",new JacksonCodec<>(GraphObject.class));
		Mono<GraphObject> res= json.get();
		res.map(graph->{
			uu=getObject();
			return null;
			}).thenReturn("ok").subscribe();
		
		assert(Objects.nonNull(uu));
	}
	
	private Object getObject() {
		System.out.println("Pase por aqui");
		return new Object();
	}
	
	@Test
	public void setLabel() throws Exception{
		RBucketReactive<String> json =  client.getBucket("prueba",StringCodec.INSTANCE);
		json.set("monitor").then().subscribe();
		assert(Objects.nonNull(json));
	}
}
