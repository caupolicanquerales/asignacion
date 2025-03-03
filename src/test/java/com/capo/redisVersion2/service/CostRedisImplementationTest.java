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

import com.capo.redisVersion2.dto.Destination;
import com.capo.redisVersion2.dto.GraphObject;
import com.capo.redisVersion2.dto.Node;
import com.capo.redisVersion2.entity.DestinationPointOfSalesMongo;
import com.capo.redisVersion2.interfaces.BasicPetitionRedis;
import com.capo.redisVersion2.interfaces.BuildingGraphRedis;
import com.capo.redisVersion2.interfaces.DijkstraAlgorithmRedis;
import com.capo.redisVersion2.repository.DestinationPointOfSalesMongoRepository;
import com.capo.redisVersion2.request.VertexRedisRequest;
import com.capo.redisVersion2.response.ResponseCostDestinations;
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
	
	@Mock
	DestinationPointOfSalesMongoRepository destinationPointOfSales;
	
	
	private VertexRedisRequest request;
	private RMapReactive<String,String> map =new MapReactiveMock();
	
	@BeforeEach
	public void setup() {
		request= new VertexRedisRequest();
		request.setCost("2");
		request.setStartVertex("1");
		request.setEndVertex("2");
	} 
	
	@Test
	public void updateCost_test() {
		DestinationPointOfSalesMongo destination= new DestinationPointOfSalesMongo();
		destination.setDestination(new Destination());
		when(petitionRedis.getReactiveMap(Mockito.any())).thenReturn(map);
		when(destinationPointOfSales.findByDestination(Mockito.any())).thenReturn(Mono.just(destination));
		when(destinationPointOfSales.save(Mockito.any())).thenReturn(Mono.just(destination));
		String result= costAndRouteRedis.updateCost(request).block();
		assert(Objects.nonNull(result));
	}
	
	@Test
	public void deleteCostAndDestination_test() {
		DestinationPointOfSalesMongo destination= new DestinationPointOfSalesMongo();
		destination.setId("1234");
		when(petitionRedis.getReactiveMap(Mockito.any())).thenReturn(map);
		when(destinationPointOfSales.findByDestination(Mockito.any())).thenReturn(Mono.just(destination));
		when(destinationPointOfSales.deleteById(Mockito.anyString())).thenReturn(Mono.empty());
		String result= costAndRouteRedis.deleteCostAndDestination(request).block();
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
	
	@Test
	public void saveAndUpdateCostAndDestinationStartingApp_test() {
		when(petitionRedis.getReactiveMap(Mockito.any())).thenReturn(map);
		String result= costAndRouteRedis.saveAndUpdateCostAndDestinationStartingApp(request);
		assert(result.equals("OK"));
	}
	
	@Test
	public void getAllCostsAndDestinations_test() {
		when(petitionRedis.getReactiveMap(Mockito.any())).thenReturn(map);
		ResponseCostDestinations result= costAndRouteRedis.getAllCostsAndDestinations().block();
		assert(result.getCostAndDestination().size()>0);
	}
	
	@Test
	public void saveCostAndDestination_test() {
		request.setEndVertex("NO");
		request.setStartVertex("NO");
		when(petitionRedis.getReactiveMap(Mockito.any())).thenReturn(map);
		when(destinationPointOfSales.save(Mockito.any())).thenReturn(Mono.just(new DestinationPointOfSalesMongo()));
		String result= costAndRouteRedis.saveCostAndDestination(request).block();
		assert(result.equals("OK"));
	}
}
