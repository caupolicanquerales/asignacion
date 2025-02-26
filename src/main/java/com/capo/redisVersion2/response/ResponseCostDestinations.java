package com.capo.redisVersion2.response;

import java.util.List;

import com.capo.redisVersion2.request.VertexRedisRequest;

public class ResponseCostDestinations {
	
	private List<VertexRedisRequest> costAndDestination;

	public List<VertexRedisRequest> getCostAndDestination() {
		return costAndDestination;
	}

	public void setCostAndDestination(List<VertexRedisRequest> costAndDestination) {
		this.costAndDestination = costAndDestination;
	}
}
