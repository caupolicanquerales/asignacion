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

import com.capo.redisVersion2.entity.PointOfSalesInMongo;
import com.capo.redisVersion2.interfaces.BasicPetitionRedis;
import com.capo.redisVersion2.repository.PointOfSaleMongoRepository;
import com.capo.redisVersion2.request.PointsRedisRequest;
import com.capo.redisVersion2.response.ResponsePointsRedis;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
class PointsOfSaleRedisImplTest {
	
	@InjectMocks
	private PointsOfSaleRedisImpl pointsOfSale;
	
	@Mock
	private BasicPetitionRedis petitionRedis;
	
	@Mock
	private PointOfSaleMongoRepository pointOfSaleMongo;
	
	private PointsRedisRequest request;
	private RMapReactive<String,String> map;
	
	@BeforeEach
	public void setup() {
		map= new MapReactiveMock();
		request= new PointsRedisRequest ();
		request.setLocation("Palermo");
		request.setId("1");
	}
	
	@Test
	public void saveAndUpdateCostPointsOfSale_test() {
		when(petitionRedis.getReactiveMap(Mockito.any())).thenReturn(map);
		Mono<String> result= pointsOfSale.updateCostPointsOfSale(request);
		assert(Objects.nonNull(result));
	}
	
	@Test
	public void removePointsOfSale_test() {
		when(petitionRedis.getReactiveMap(Mockito.any())).thenReturn(map);
		Mono<String> result= pointsOfSale.removePointsOfSale(request);
		assert(Objects.nonNull(result));
	}
	
	@Test
	public void getPointsOfSale_test() {
		when(petitionRedis.getReactiveMap(Mockito.any())).thenReturn(map);
		ResponsePointsRedis result= pointsOfSale.getPointsOfSale().block();
		assert(Objects.nonNull(result));
	}
	
	@Test
	public void savePointsOfSaleStartingApp_test() {
		when(petitionRedis.getReactiveMap(Mockito.any())).thenReturn(map);
		String result= pointsOfSale.savePointsOfSaleStartingApp(request);
		assert(result.equals("OK"));
	}
	
	@Test
	public void savePointsOfSale_test() {
		request.setLocation("NO,NO");
		when(petitionRedis.getReactiveMap(Mockito.any())).thenReturn(map);
		when(pointOfSaleMongo.save(Mockito.any())).thenReturn(Mono.just(new PointOfSalesInMongo()));
		String result= pointsOfSale.savePointsOfSale(request).block();
		assert(result.equals("OK"));
	}
}
