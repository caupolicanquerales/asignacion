package com.capo.redisVersion2.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.capo.redisVersion2.dto.Node;
import com.capo.redisVersion2.interfaces.WeightedGraph;


@Service
public class WeightedGraphImpl implements WeightedGraph {
	
	private Map<Integer,List<Node>> adj;
	
	public WeightedGraphImpl() {
		this.adj = new HashMap<Integer, List<Node>>();
	}
	
	@Override
	public void createVertex(Integer vertex) {
		adj.put(vertex, new ArrayList<Node>());
	}
	
	@Override
	public void addEdge(Integer u, Integer v, Integer weight) {
		List<Node> resultU= adj.get(u);
		resultU.add(new Node(v,weight));
	}
	
	@Override
	public Map<Integer,List<Node>> getGraph(){
		return adj;
	}
}
