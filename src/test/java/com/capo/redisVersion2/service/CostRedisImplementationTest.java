package com.capo.redisVersion2.service;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.redisson.api.RMapReactive;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.capo.redisVersion2.dto.GraphObject;
import com.capo.redisVersion2.dto.Node;
import com.capo.redisVersion2.interfaces.BasicPetitionRedis;
import com.capo.redisVersion2.interfaces.BuildingGraphRedis;
import com.capo.redisVersion2.interfaces.DijkstraAlgorithmRedis;
import com.capo.redisVersion2.request.VertexRedisRequest;
import com.capo.redisVersion2.response.ResponseGraphRedis;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
public class CostRedisImplementationTest {
	
	@InjectMocks
	CostRedisImplementation costAndRouteRedis;
	
	@Mock
	private BasicPetitionRedis petitionRedis;
	
	@Mock
	private BuildingGraphRedis buildingGraph;
	
	@Mock
	private DijkstraAlgorithmRedis dijkstraAlgorithm;
	
	
	private VertexRedisRequest request;
	private RMapReactive<String,String> map;
	
	@BeforeEach
	public void setup() {
		request= new VertexRedisRequest();
		request.setCost("2");
		request.setStartVertex("1");
		request.setEndVertex("2");
		map= new MapReactiveMock();
	} 
	
	@Test
	public void saveAndUpdateCostAndDestination_test() {
		when(petitionRedis.getReactiveMap(Mockito.any())).thenReturn(map);
		Mono<String> result= costAndRouteRedis.saveAndUpdateCostAndDestination(request);
		assert(Objects.nonNull(result));
	}
	
	@Test
	public void removeCostAndDestination_test() {
		when(petitionRedis.getReactiveMap(Mockito.any())).thenReturn(map);
		Mono<String> result= costAndRouteRedis.removeCostAndDestination(request);
		assert(Objects.nonNull(result));
	}
	
	@Test
	public void buildingGraph_test() {
		when(petitionRedis.getReactiveJsonBucket(Mockito.any())).thenReturn(Mono.just(new GraphObject()));
		when(petitionRedis.saveReactiveJsonBucket(Mockito.any(),Mockito.any())).thenReturn("OK");
		when(buildingGraph.buildingGraph()).thenReturn(Mono.just(new GraphObject()));
		String result= costAndRouteRedis.buildingGraph().block();
		assert(result.equals("OK"));
	}
	
	@Test
	public void estimationOfCosts_test() {
		GraphObject graphObject= new GraphObject();
		Map<String,List<Node>> map = new HashMap<String,List<Node>>();
		graphObject.setGraphObject(map);
		when(petitionRedis.getReactiveJsonBucket(Mockito.any())).thenReturn(Mono.just(graphObject));
		when(dijkstraAlgorithm.dijkstra(map, 0)).thenReturn(new HashMap<>());
		ResponseGraphRedis result= costAndRouteRedis.estimationOfCosts("2").block();
		assert(Objects.nonNull(result));
	}
	
}
