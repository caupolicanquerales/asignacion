package com.capo.redisVersion2.dto;

public class Node {
	private int vertex;
	private int weight;
	
	public Node(int vertex,int weight) {
		this.vertex=vertex;
		this.weight=weight;
	}

	public int getVertex() {
		return vertex;
	}

	public void setVertex(int vertex) {
		this.vertex = vertex;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
