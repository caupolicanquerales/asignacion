package com.capo.redisVersion2.dto;

import java.io.Serializable;

public class Node implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int vertex;
	private int weight;
	
	public Node() {
	}
	
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
