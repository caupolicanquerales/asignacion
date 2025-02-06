package com.capo.redisVersion2.service;

import java.util.List;
import java.util.Map;

import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capo.redisVersion2.dto.GraphObject;
import com.capo.redisVersion2.dto.Node;
import com.capo.redisVersion2.dto.WeightedGraphObject;
import com.capo.redisVersion2.interfaces.BuildingGraphRedis;

import reactor.core.publisher.Mono;


@Service
public class BuildingGraphRedisImpl implements BuildingGraphRedis {
	
	private static final String MAP_COST= "costos";
	private static final String MAP_STORES= "puntos";
	
	@Autowired
	private RedissonReactiveClient client;
	
	@Override
	public Mono<GraphObject> buildingGraph() {
		Mono<Map<String,String>> mapStores= getMapStores();
		Mono<Map<String,String>> mapCost= getMapCost();
		return Mono.zip(mapStores, mapCost).map(tuple->processOfBuildingGraph(tuple.getT1(),tuple.getT2()));
	}
	
	private Mono<Map<String,String>> getMapCost() {
		RMapReactive<String,String> map= this.client.getMap(MAP_COST,StringCodec.INSTANCE);
		return map.entryIterator().collectMap(Map.Entry::getKey,Map.Entry::getValue);
	}
	
	private Mono<Map<String,String>> getMapStores() {
		RMapReactive<String,String> map= this.client.getMap(MAP_STORES,StringCodec.INSTANCE);
		return map.entryIterator().collectMap(Map.Entry::getKey,Map.Entry::getValue);
	}
	
	private GraphObject processOfBuildingGraph(Map<String,String> mapStores,Map<String,String> mapCost){
		WeightedGraphObject weightedGraphObject= getWeightedGraph(mapStores);
		return fillWeightedGraph(mapCost, weightedGraphObject);
	}
	
	private WeightedGraphObject getWeightedGraph(Map<String,String> mapStores) {
		WeightedGraphObject objectGraph= new WeightedGraphObject();
		for(int i=0;i<mapStores.size();i++) {
			objectGraph.createVertex(String.valueOf(i+1));
		}
		return objectGraph;
	}
	
	private GraphObject fillWeightedGraph(Map<String,String> mapCost, WeightedGraphObject objectGraph) {
		for(Map.Entry<String,String> entry:mapCost.entrySet()) {
			String[] stores= entry.getKey().split(",");
			objectGraph.addEdge(stores[0],Integer.valueOf(stores[1]),Integer.valueOf(entry.getValue()));
		}
		return getGraphObject(objectGraph.getGraph());
	}
	
	private GraphObject getGraphObject(Map<String,List<Node>> graphMap) {
		GraphObject graphObject = new GraphObject();
		graphObject.setGraphObject(graphMap);
		return graphObject; 
	} 	
}
