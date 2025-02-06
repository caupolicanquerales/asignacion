package com.capo.redisVersion2.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightedGraphObject {
	
	private Map<String,List<Node>> adj;
	
	public WeightedGraphObject() {
		this.adj = new HashMap<String, List<Node>>();
	}
	
	public void createVertex(String vertex) {
		adj.put(vertex, new ArrayList<Node>());
	}
	
	public void addEdge(String u, Integer v, Integer weight) {
		List<Node> resultU= adj.get(u);
		resultU.add(new Node(v,weight));
	}
	
	public Map<String,List<Node>> getGraph(){
		return adj;
	}
}
