package com.capo.redisVersion2.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.springframework.stereotype.Service;

import com.capo.redisVersion2.dto.Node;
import com.capo.redisVersion2.interfaces.DijkstraAlgorithm;


@Service
public class DijkstraAlgorithmImpl implements DijkstraAlgorithm {
	
	@Override
	public Map<String,Map<Integer,String>> dijkstra(Map<Integer,List<Node>> adj, int source) {
		int[] distance = distanceArray(adj,source);
		String[] routes = routeArray(adj,source);
		PriorityQueue<Node> pq = new PriorityQueue<>(
	            (v1, v2) -> v1.getWeight() - v2.getWeight());
	        pq.add(new Node(source, 0));
	   while(pq.size()>0) {
		   Node current = pq.poll();
		   for(Node node: adj.get(current.getVertex())) {
			   if((distance[current.getVertex()]+node.getWeight())<distance[node.getVertex()]){
				   distance[node.getVertex()]= node.getWeight()+ distance[current.getVertex()];
				   routes[node.getVertex()]= routes[current.getVertex()].concat("-"+String.valueOf(node.getVertex()));
				   pq.add(new Node(node.getVertex(),distance[node.getVertex()]));
			   }
		   }
	   }
	   return getResult(distance,routes,source);
	}
	
	private int[] distanceArray(Map<Integer,List<Node>> adj,int source) {
		int[] distance = new int[adj.size()+1];
		for(int i=0;i<adj.size();i++) {
			distance[i+1]= Integer.MAX_VALUE;
		}
		distance[source]=0;
		return distance;
	}
	
	private String[] routeArray(Map<Integer,List<Node>> adj,int source) {
		String[] route = new String[adj.size()+1];
		for(int i=0;i<adj.size();i++) {
			route[i+1]=String.valueOf(source);
		}
		route[0]=String.valueOf(source);
		return route;
	}
	
	private Map<Integer,String> buildMapWeight(int[] distance) {
		Map<Integer,String> mapWeight= new HashMap<Integer, String>();
		for(int i=0;i<distance.length;i++) {
			if(distance[i]!=Integer.MAX_VALUE && distance[i]!=0) {
				mapWeight.put(Integer.valueOf(i), String.valueOf(distance[i]));
			}
		}
		return mapWeight;
	}
	
	private Map<Integer,String> buildMapRoute(String[] routes, int source) {
		Map<Integer,String> mapRoute= new HashMap<Integer, String>();
		for(int i=0;i<routes.length;i++) {
			if(!routes[i].equals(String.valueOf(source))) {
				mapRoute.put(Integer.valueOf(i),routes[i]);
			}
		}
		return mapRoute;
	}
	
	private Map<String,Map<Integer,String>> getResult(int[] distance,String[] routes, int source){
		Map<Integer,String> weight= buildMapWeight(distance);
		Map<Integer,String> route = buildMapRoute(routes,source);
		Map<String,Map<Integer,String>> mapResult= new HashMap<String, Map<Integer,String>>();
		mapResult.put("weight", weight);
		mapResult.put("route", route);
		return mapResult; 
	}
}
