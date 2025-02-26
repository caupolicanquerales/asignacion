package com.capo.redisVersion2.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.redisson.api.RMapReactive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capo.redisVersion2.dto.CostAndDestination;
import com.capo.redisVersion2.dto.GraphObject;
import com.capo.redisVersion2.entity.DestinationPointOfSalesMongo;
import com.capo.redisVersion2.enums.RedisEnum;
import com.capo.redisVersion2.interfaces.BasicPetitionRedis;
import com.capo.redisVersion2.interfaces.BuildingGraphRedis;
import com.capo.redisVersion2.interfaces.CostAndRouteRedis;
import com.capo.redisVersion2.interfaces.DijkstraAlgorithmRedis;
import com.capo.redisVersion2.repository.DestinationPointOfSalesMongoRepository;
import com.capo.redisVersion2.request.VertexRedisRequest;
import com.capo.redisVersion2.response.ResponseCostDestinations;
import com.capo.redisVersion2.response.ResponseGraphRedis;

import reactor.core.publisher.Mono;

@Service
public class CostRedisImplementation implements CostAndRouteRedis {
	
	@Autowired
	private BuildingGraphRedis buildingGraph;
	
	@Autowired
	private DijkstraAlgorithmRedis dijkstraAlgorithm;
	
	@Autowired
	private BasicPetitionRedis petitionRedis;
	
	@Autowired
	DestinationPointOfSalesMongoRepository destinationPointOfSales;
	
	@Override
	public Mono<String> buildingGraph(){
		return buildingGraph.buildingGraph()
				.map(this::saveGraphInRedis);
	}
	
	@Override
	public Mono<String> saveAndUpdateCostAndDestination(VertexRedisRequest request) {
		String key= request.getStartVertex()+","+request.getEndVertex();
		RMapReactive<String,String> map = this.petitionRedis.getReactiveMap(RedisEnum.MAP_COST.value);
		map.put(key, request.getCost()).then().subscribe();
		return Mono.just("OK");
	}
	
	@Override
	public Mono<String> removeCostAndDestination(VertexRedisRequest request) {
		String key= request.getStartVertex()+","+request.getEndVertex();
		RMapReactive<String,String> map = this.petitionRedis.getReactiveMap(RedisEnum.MAP_COST.value);
		map.remove(key).then().subscribe();
		return Mono.just("OK");
	}
	
	@Override
	public Mono<ResponseGraphRedis> estimationOfCosts(String vertex) {
		return this.petitionRedis.getReactiveJsonBucket(RedisEnum.GRAPH.value)
			.map(graph->dijkstraAlgorithm.dijkstra(graph.getGraphObject(), Integer.valueOf(vertex)))
			.map(this::getResponseGraph);
	}
	
	@Override
	public String saveAndUpdateCostAndDestinationStartingApp(VertexRedisRequest request) {
		String key= request.getStartVertex()+","+request.getEndVertex();
		RMapReactive<String,String> map = this.petitionRedis.getReactiveMap(RedisEnum.MAP_COST.value);
		map.put(key, request.getCost()).then().subscribe();
		return "OK";
	}
	
	@Override
	public Mono<ResponseCostDestinations> getAllCostsAndDestinations() {
		RMapReactive<String,String> map = this.petitionRedis.getReactiveMap(RedisEnum.MAP_COST.value);
		return map.entryIterator().map(this::getCostAndDestination)
			.collect(Collectors.toList())
			.map(this::getResponseCostDestinations);
	}
	
	
	@Override
	public Mono<String> saveCostAndDestination(VertexRedisRequest request) {
		String key= request.getStartVertex()+","+request.getEndVertex();
		RMapReactive<String,String> map = this.petitionRedis.getReactiveMap(RedisEnum.MAP_COST.value);
		return Mono.just(map.get(key)).flatMap(item->item)
		.hasElement().map(element->{
			if(!element) {
				map.put(key, request.getCost()).then().subscribe();
				return saveCostAndDestinationInMongo(request);
			}
			return Mono.just("ERROR");
		}).flatMap(result->result);
	}
	
	private Mono<String> saveCostAndDestinationInMongo(VertexRedisRequest request) {
		CostAndDestination costAndDestination = getCostAndDestination(request);
		DestinationPointOfSalesMongo destination =getDestinationPointOfSalesMongo(costAndDestination);
		return destinationPointOfSales.save(destination).map(result->{
			return "OK";
		});
	}
	
	private DestinationPointOfSalesMongo getDestinationPointOfSalesMongo(CostAndDestination destination) {
		DestinationPointOfSalesMongo destinationMongo = new DestinationPointOfSalesMongo();
		destinationMongo.setCostAndDestination(destination);
		return destinationMongo;
	}
	
	private CostAndDestination getCostAndDestination(VertexRedisRequest request) {
		CostAndDestination costAndDestination = new CostAndDestination();
		costAndDestination.setCost(request.getCost());
		costAndDestination.setStartVertex(request.getStartVertex());
		costAndDestination.setEndVertex(request.getEndVertex());
		return costAndDestination;
	}
	
	private VertexRedisRequest getCostAndDestination(Map.Entry<String, String> entry) {
		VertexRedisRequest destination = new VertexRedisRequest();
		String[] vertex= entry.getKey().split(","); 
		destination.setCost(entry.getValue());
		destination.setStartVertex(vertex[0]);
		destination.setEndVertex(vertex[1]);
		return destination;
	}
	
	private ResponseCostDestinations getResponseCostDestinations(List<VertexRedisRequest> list) {
		ResponseCostDestinations points = new ResponseCostDestinations();
		points.setCostAndDestination(list);
		return points;
	}
	
	private ResponseGraphRedis getResponseGraph(Map<String,Map<Integer,String>> resultDij) {
		ResponseGraphRedis result = new ResponseGraphRedis();
		result.setResult(resultDij);
		return result;
	}
	
	private String saveGraphInRedis(GraphObject graph){
		return  this.petitionRedis.saveReactiveJsonBucket(RedisEnum.GRAPH.value, graph);
	}
}
