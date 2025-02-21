package com.capo.redisVersion2.response;

import java.util.List;

import com.capo.redisVersion2.dto.Point;

public class ResponsePointsRedis {
	private List<Point> points;

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}
}
