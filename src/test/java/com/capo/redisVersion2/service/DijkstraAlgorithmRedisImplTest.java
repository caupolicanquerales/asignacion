package com.capo.redisVersion2.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.capo.redisVersion2.dto.Node;

import io.jsonwebtoken.lang.Arrays;

@ExtendWith(SpringExtension.class)
class DijkstraAlgorithmRedisImplTest {
	
	@InjectMocks
	DijkstraAlgorithmRedisImpl dijkstraAlgorithm;
	
	private Map<String,List<Node>> adj;
	
	@BeforeEach
	public void setup() {
		adj= new HashMap<String, List<Node>>();
		Node node12= new Node();
		node12.setVertex(2);
		node12.setWeight(2);
		Node node13= new Node();
		node13.setVertex(3);
		node13.setWeight(3);
		Node node14= new Node();
		node14.setVertex(4);
		node14.setWeight(11);
		Node node23= new Node();
		node23.setVertex(3);
		node23.setWeight(3);
		Node node24= new Node();
		node24.setVertex(4);
		node24.setWeight(10);
		Node node25= new Node();
		node25.setVertex(5);
		node25.setWeight(14);
		Node node38= new Node();
		node38.setVertex(8);
		node38.setWeight(10);
		Node node45= new Node();
		node45.setVertex(5);
		node45.setWeight(5);
		Node node46= new Node();
		node46.setVertex(6);
		node46.setWeight(6);
		Node node58= new Node();
		node58.setVertex(8);
		node58.setWeight(30);
		Node node67= new Node();
		node67.setVertex(7);
		node67.setWeight(32);
		Node node89= new Node();
		node89.setVertex(9);
		node89.setWeight(11);
		Node node107= new Node();
		node107.setVertex(7);
		node107.setWeight(5);
		adj.put("1", Arrays.asList(new Node[] {node12,node13,node14}));
		adj.put("2", Arrays.asList(new Node[] {node23,node24,node25}));
		adj.put("3", Arrays.asList(new Node[] {node38}));
		adj.put("4", Arrays.asList(new Node[] {node45}));
		adj.put("5", Arrays.asList(new Node[] {node58}));
		adj.put("6", Arrays.asList(new Node[] {node67}));
		adj.put("7", Arrays.asList(new Node[] {}));
		adj.put("8", Arrays.asList(new Node[] {node89}));
		adj.put("9", Arrays.asList(new Node[] {}));
		adj.put("10", Arrays.asList(new Node[] {node107}));
	}
	
	@Test
	void dijkstraCostFromPointOfSale1To3_test() {
		Map<String,Map<Integer,String>> result= dijkstraAlgorithm.dijkstra(adj, 1);
		Map<Integer,String> costs=result.get("weight");
		assert(costs.get(3).equals("3"));
	}

}
