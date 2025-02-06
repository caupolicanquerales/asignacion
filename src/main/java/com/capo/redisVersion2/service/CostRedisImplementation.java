package com.capo.redisVersion2.service;

import java.util.Map;

import org.redisson.api.RJsonBucketReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.JacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capo.redisVersion2.dto.GraphObject;
import com.capo.redisVersion2.interfaces.BuildingGraphRedis;
import com.capo.redisVersion2.interfaces.CostAndRouteRedis;
import com.capo.redisVersion2.interfaces.DijkstraAlgorithmRedis;
import com.capo.redisVersion2.request.RequestVertexRedis;
import com.capo.redisVersion2.response.ResponseGraphRedis;

import reactor.core.publisher.Mono;

@Service
public class CostRedisImplementation implements CostAndRouteRedis {
	
	@Autowired
	private BuildingGraphRedis buildingGraph;
	
	@Autowired
	private DijkstraAlgorithmRedis dijkstraAlgorithm;
	
	@Autowired
	private RedissonReactiveClient client;
	
	private final static String MAP_COST = "costos";
	private final static String GRAPH = "graph";
	
	@Override
	public Mono<String> buildingGraph(){
		return buildingGraph.buildingGraph()
				.map(this::saveGraphInRedis);
	}
	
	@Override
	public Mono<String> saveAndUpdateCostAndDestination(RequestVertexRedis request) {
		String key= request.getStartVertex()+","+request.getEndVertex();
		RMapReactive<String,String> map =  client.getMap(MAP_COST,StringCodec.INSTANCE);
		map.put(key, request.getCost()).then().subscribe();
		return Mono.just("OK");
	}
	
	@Override
	public Mono<String> removeCostAndDestination(RequestVertexRedis request) {
		String key= request.getStartVertex()+","+request.getEndVertex();
		RMapReactive<String,String> map =  client.getMap(MAP_COST,StringCodec.INSTANCE);
		map.remove(key).then().subscribe();
		return Mono.just("OK");
	}
	
	@Override
	public Mono<ResponseGraphRedis> estimationOfCosts(String vertex) {
		RJsonBucketReactive<GraphObject> json =  client.getJsonBucket(GRAPH,new JacksonCodec<>(GraphObject.class));
		Mono<GraphObject> graphObject= json.get();
		return graphObject.map(graph->dijkstraAlgorithm.dijkstra(graph.getGraphObject(), Integer.valueOf(vertex)))
		.map(this::getResponseGraph);
	}
	
	private ResponseGraphRedis getResponseGraph(Map<String,Map<Integer,String>> resultDij) {
		ResponseGraphRedis result = new ResponseGraphRedis();
		result.setResult(resultDij);
		return result;
	}
	
	private String saveGraphInRedis(GraphObject graph){
		RJsonBucketReactive<GraphObject> json =  client.getJsonBucket(GRAPH,new JacksonCodec<>(GraphObject.class));
		json.set(graph).then().subscribe();
		return "OK";
	}
}
