package com.capo.redisVersion2.service;

import java.util.List;
import java.util.Map;

import org.redisson.api.RBucketReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capo.redisVersion2.dto.GraphObject;
import com.capo.redisVersion2.dto.Node;
import com.capo.redisVersion2.interfaces.BuildingGraph;
import com.capo.redisVersion2.interfaces.ConvertToJson;

import reactor.core.publisher.Mono;

@Service
public class CostImplementation {
	
	@Autowired
	private BuildingGraph buildingGraph;
	
	@Autowired
	private ConvertToJson convertToJson;
	
	@Autowired
	private RedissonReactiveClient client;
	
	public void buildingGraph() throws Exception {
		try {
			Map<Integer,List<Node>> graph = buildingGraph.buildingGraph();
			GraphObject graphObject = new GraphObject();
			graphObject.setGraphObject(graph);
			String json= convertToJson.convertToJson(graphObject);
			RBucketReactive<String> bucket = client.getBucket("graph", StringCodec.INSTANCE);
			Mono<Void> set = bucket.set(json);
			System.out.println("pase graph");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error en construir el graph");
		}
	}
}
