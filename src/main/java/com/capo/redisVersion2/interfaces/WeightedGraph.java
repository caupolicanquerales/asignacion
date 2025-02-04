package com.capo.redisVersion2.interfaces;

import java.util.List;
import java.util.Map;

import com.capo.redisVersion2.dto.Node;


public interface WeightedGraph {
	void createVertex(Integer vertex);
	void addEdge(Integer u, Integer v, Integer weight);
	Map<Integer,List<Node>> getGraph();
}
