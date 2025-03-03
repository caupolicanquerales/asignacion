package com.capo.redisVersion2.controller;

import static org.mockito.Mockito.when;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.capo.redisVersion2.interfaces.PointsOfSaleRedis;
import com.capo.redisVersion2.request.PointsRedisRequest;
import com.capo.redisVersion2.response.ResponsePointsRedis;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
class PointsOfSaleRedisControllerTest { 
	
	@InjectMocks
	PointsOfSaleRedisController pointsOfSaleController;
	
	@Mock
	PointsOfSaleRedis pointsOfSale;
	
	private PointsRedisRequest request;
	
	@BeforeEach
	public void setup() {
		request= new PointsRedisRequest ();
	}

	@Test
	void saveAndUpdatePointsOfsale_test() {
		when(pointsOfSale.updateCostPointsOfSale(Mockito.any())).thenReturn(Mono.just("OK"));
		ResponseEntity<String> response=pointsOfSaleController.saveAndUpdatePointsOfsale(request).block();
		assert(response.getBody().equals("OK"));
	}
	
	@Test
	void removePointsOfSale_test() {
		when(pointsOfSale.removePointsOfSale(Mockito.any())).thenReturn(Mono.just("OK"));
		ResponseEntity<String> response=pointsOfSaleController.removePointsOfSale(request).block();
		assert(response.getBody().equals("OK"));
	}
	
	@Test
	void getPointsOfSale_test() {
		when(pointsOfSale.getPointsOfSale()).thenReturn(Mono.just(new ResponsePointsRedis()));
		ResponseEntity<ResponsePointsRedis> response=pointsOfSaleController.getPointsOfSale(request).block();
		assert(Objects.nonNull(response.getBody()));
	}

}
