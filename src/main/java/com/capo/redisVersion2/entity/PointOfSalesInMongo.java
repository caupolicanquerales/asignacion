package com.capo.redisVersion2.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.capo.redisVersion2.dto.Point;

@Document(collection="points")
public class PointOfSalesInMongo {
	
	@Id
	private String id;
	private Point  point;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
}
