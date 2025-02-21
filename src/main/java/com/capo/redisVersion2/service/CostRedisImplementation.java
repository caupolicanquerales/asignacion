package com.capo.redisVersion2.service;

import java.util.Map;

import org.redisson.api.RMapReactive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capo.redisVersion2.dto.GraphObject;
import com.capo.redisVersion2.enums.RedisEnum;
import com.capo.redisVersion2.interfaces.BasicPetitionRedis;
import com.capo.redisVersion2.interfaces.BuildingGraphRedis;
import com.capo.redisVersion2.interfaces.CostAndRouteRedis;
import com.capo.redisVersion2.interfaces.DijkstraAlgorithmRedis;
import com.capo.redisVersion2.request.VertexRedisRequest;
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
	
	private ResponseGraphRedis getResponseGraph(Map<String,Map<Integer,String>> resultDij) {
		ResponseGraphRedis result = new ResponseGraphRedis();
		result.setResult(resultDij);
		return result;
	}
	
	private String saveGraphInRedis(GraphObject graph){
		return  this.petitionRedis.saveReactiveJsonBucket(RedisEnum.GRAPH.value, graph);
	}
}
