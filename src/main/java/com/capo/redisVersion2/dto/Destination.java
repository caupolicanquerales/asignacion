package com.capo.redisVersion2.dto;

public class Destination {
	private String startVertex;
	private String endVertex;
	private String cost;
	
	public String getStartVertex() {
		return startVertex;
	}
	public void setStartVertex(String startVertex) {
		this.startVertex = startVertex;
	}
	public String getEndVertex() {
		return endVertex;
	}
	public void setEndVertex(String endVertex) {
		this.endVertex = endVertex;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
}
