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

import com.capo.redisVersion2.interfaces.CostAndRouteRedis;
import com.capo.redisVersion2.request.VertexRedisRequest;
import com.capo.redisVersion2.response.ResponseGraphRedis;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
class CostPointsOfSaleRedisControllerTest {
	
	@InjectMocks
	private CostPointsOfSaleRedisController costPointsOfSale;
	
	@Mock
	private CostAndRouteRedis costAndRoute;
	
	private VertexRedisRequest request;
	
	@BeforeEach
	public void setup() {
		request = new VertexRedisRequest();
	}

	@Test
	void updateCostAndDestination_test() {
		when(costAndRoute.updateCost(Mockito.any())).thenReturn(Mono.just("OK"));
		Mono<ResponseEntity<String>> response=costPointsOfSale.updateCostAndDestination(request);
		ResponseEntity<String> result= response.block();
		assert(result.getBody().equals("OK"));
	}
	
	@Test
	void deleteCostAndDestination_test() {
		when(costAndRoute.deleteCostAndDestination(Mockito.any())).thenReturn(Mono.just("OK"));
		Mono<ResponseEntity<String>> response=costPointsOfSale.deleteCostAndDestination(request);
		ResponseEntity<String> result= response.block();
		assert(result.getBody().equals("OK"));
	}

	@Test
	void getPricesFromVertex_test() {
		when(costAndRoute.estimationOfCosts(Mockito.any())).thenReturn(Mono.just(new ResponseGraphRedis()));
		Mono<ResponseEntity<ResponseGraphRedis>> response=costPointsOfSale.getPricesFromVertex(request);
		ResponseEntity<ResponseGraphRedis> result= response.block();
		assert(Objects.nonNull(result.getBody()));
	}
	
	@Test
	void buildingGraph_test() {
		when(costAndRoute.buildingGraph()).thenReturn(Mono.just("OK"));
		Mono<ResponseEntity<String>> response=costPointsOfSale.buildingGraph(request);
		ResponseEntity<String> result= response.block();
		assert(result.getBody().equals("OK"));
	}
}
