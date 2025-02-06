package com.capo.redisVersion2.response;

import java.util.List;

import com.capo.redisVersion2.dto.PointsOfSale;

public class ResponsePointsRedis {
	private List<PointsOfSale> points;

	public List<PointsOfSale> getPoints() {
		return points;
	}

	public void setPoints(List<PointsOfSale> points) {
		this.points = points;
	}
}
