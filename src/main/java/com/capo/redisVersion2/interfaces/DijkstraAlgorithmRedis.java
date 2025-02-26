package com.capo.redisVersion2.interfaces;

import java.util.List;
import java.util.Map;

import com.capo.redisVersion2.dto.Node;

public interface DijkstraAlgorithmRedis {
	Map<String,Map<Integer,String>> dijkstra(Map<String,List<Node>> adj, int source);
}
