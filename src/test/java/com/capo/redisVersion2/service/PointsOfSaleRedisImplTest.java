package com.capo.redisVersion2.service;

import static org.mockito.Mockito.when;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.redisson.api.RMapReactive;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.capo.redisVersion2.interfaces.BasicPetitionRedis;
import com.capo.redisVersion2.request.PointsRedisRequest;
import com.capo.redisVersion2.response.ResponsePointsRedis;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
class PointsOfSaleRedisImplTest {
	
	@InjectMocks
	private PointsOfSaleRedisImpl pointsOfSale;
	
	@Mock
	private BasicPetitionRedis petitionRedis;
	
	private PointsRedisRequest request;
	
	@BeforeEach
	public void setup() {
		request= new PointsRedisRequest ();
		request.setLocation("Palermo");
		request.setId("1");
	}
	
	@Test
	void saveAndUpdateCostPointsOfSale_test() {
		RMapReactive<String,String> map= new MapReactiveMock();
		when(petitionRedis.getReactiveMap(Mockito.any())).thenReturn(map);
		Mono<String> result= pointsOfSale.saveAndUpdateCostPointsOfSale(request);
		assert(Objects.nonNull(result));
	}
	
	@Test
	void removePointsOfSale_test() {
		RMapReactive<String,String> map= new MapReactiveMock();
		when(petitionRedis.getReactiveMap(Mockito.any())).thenReturn(map);
		Mono<String> result= pointsOfSale.removePointsOfSale(request);
		assert(Objects.nonNull(result));
	}
	
	@Test
	void getPointsOfSale_test() {
		RMapReactive<String,String> map= new MapReactiveMock();
		when(petitionRedis.getReactiveMap(Mockito.any())).thenReturn(map);
		ResponsePointsRedis result= pointsOfSale.getPointsOfSale().block();
		assert(Objects.nonNull(result));
	}
}
