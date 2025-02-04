package com.capo.redisVersion2.service;

import java.util.List;
import java.util.Map;

import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capo.redisVersion2.dto.Node;
import com.capo.redisVersion2.dto.WeightedGraphObject;
import com.capo.redisVersion2.interfaces.BuildingGraph;


@Service
public class BuildingGraphImpl implements BuildingGraph {
	
	private static final String MAP_COST= "costos";
	private static final String MAP_STORES= "puntos";
	
	@Autowired
	private RedissonReactiveClient client;
	
	@Override
	public Map<Integer,List<Node>> buildingGraph() {
		Map<String,String> mapStores= getMapStores();
		Map<String,String> mapCost =getMapCost();
		WeightedGraphObject weightedGraphObject = getWeightedGraph(mapStores);
		return fillWeightedGraph(mapCost, weightedGraphObject);
	}
	
	private Map<String,String> getMapCost() {
		RMapReactive<String,String> map= this.client.getMap(MAP_COST,StringCodec.INSTANCE);
		return  map.entryIterator().collectMap(Map.Entry::getKey,Map.Entry::getValue).block();
	}
	
	private Map<String,String> getMapStores() {
		RMapReactive<String,String> map= this.client.getMap(MAP_STORES,StringCodec.INSTANCE);
		return  map.entryIterator().collectMap(Map.Entry::getKey,Map.Entry::getValue).block();
	}
	
	private WeightedGraphObject getWeightedGraph(Map<String,String> mapStores) {
		WeightedGraphObject objectGraph= new WeightedGraphObject();
		for(int i=0;i<mapStores.size();i++) {
			objectGraph.createVertex(i+1);
		}
		return objectGraph;
	}
	
	private Map<Integer,List<Node>> fillWeightedGraph(Map<String,String> mapCost, WeightedGraphObject objectGraph) {
		for(Map.Entry<String,String> entry:mapCost.entrySet()) {
			String[] stores= entry.getKey().split(",");
			objectGraph.addEdge(Integer.valueOf(stores[0]),Integer.valueOf(stores[1]),Integer.valueOf(entry.getValue()));
		}
		return objectGraph.getGraph();
	}
}
