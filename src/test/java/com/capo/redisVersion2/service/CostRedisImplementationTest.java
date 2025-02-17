package com.capo.redisVersion2.service;

import static org.mockito.Mockito.when;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.redisson.api.RMapReactive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capo.redisVersion2.dto.GraphObject;
import com.capo.redisVersion2.interfaces.BasicPetitionRedis;
import com.capo.redisVersion2.interfaces.BuildingGraphRedis;
import com.capo.redisVersion2.interfaces.CostAndRouteRedis;
import com.capo.redisVersion2.interfaces.DijkstraAlgorithmRedis;
import com.capo.redisVersion2.request.VertexRedisRequest;

import reactor.core.publisher.Mono;

@SpringBootTest
public class CostRedisImplementationTest {
	
	@Autowired
	CostAndRouteRedis costAndRouteRedis;
	
	@Mock
	private BasicPetitionRedis petitionRedis;
	
	@Mock
	private BuildingGraphRedis buildingGraph;
	
	private DijkstraAlgorithmRedis dijkstraAlgorithm = Mockito.mock(DijkstraAlgorithmRedisImpl.class);
	
	
	private VertexRedisRequest request;
	
	@BeforeEach
	public void setup() {
		request= new VertexRedisRequest();
		request.setCost("2");
		request.setStartVertex("1");
		request.setEndVertex("2");
	} 
	
	@Test
	public void saveAndUpdateCostAndDestination_test() {
		RMapReactive<String,String> map= Mockito.mock(RMapReactive.class);
		when(petitionRedis.getReactiveMap(Mockito.any())).thenReturn(map);
		Mono<String> result= costAndRouteRedis.saveAndUpdateCostAndDestination(request);
		assert(Objects.nonNull(result));
	}
	
	@Test
	public void removeCostAndDestination_test() {
		RMapReactive<String,String> map= Mockito.mock(RMapReactive.class);
		when(petitionRedis.getReactiveMap(Mockito.any())).thenReturn(map);
		Mono<String> result= costAndRouteRedis.removeCostAndDestination(request);
		assert(Objects.nonNull(result));
	}
	
	@Test
	public void buildingGraph_test() {
		when(petitionRedis.getReactiveJsonBucket(Mockito.any())).thenReturn(Mono.just(new GraphObject()));
		when(buildingGraph.buildingGraph()).thenReturn(Mono.just(new GraphObject()));
		String result= costAndRouteRedis.buildingGraph().block();
		assert(result.equals("OK"));
	}
	/*
	@Test
	public void estimationOfCosts_test() {
		GraphObject graphObject= new GraphObject();
		Map<String,List<Node>> map = new HashMap<String, List<Node>>();
		graphObject.setGraphObject(map);
		when(petitionRedis.getReactiveJsonBucket(Mockito.any())).thenReturn(Mono.just(graphObject));
		when(dijkstraAlgorithm.dijkstra(map, 0)).thenReturn(new HashMap<>());
		ResponseGraphRedis result= costAndRouteRedis.estimationOfCosts("2").block();
		assert(Objects.nonNull(result));
	}*/
	
}
