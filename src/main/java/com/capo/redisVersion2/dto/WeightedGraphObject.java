package com.capo.redisVersion2.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightedGraphObject {
	
	private Map<Integer,List<Node>> adj;
	
	public WeightedGraphObject() {
		this.adj = new HashMap<Integer, List<Node>>();
	}
	
	public void createVertex(Integer vertex) {
		adj.put(vertex, new ArrayList<Node>());
	}
	
	public void addEdge(Integer u, Integer v, Integer weight) {
		List<Node> resultU= adj.get(u);
		resultU.add(new Node(v,weight));
	}
	
	public Map<Integer,List<Node>> getGraph(){
		return adj;
	}
}
