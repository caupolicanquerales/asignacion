package com.capo.redisVersion2.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.capo.redisVersion2.dto.Destination;

@Document(collection="costs")
public class DestinationPointOfSalesMongo {
	
	@Id
	private String id;
	private Destination destination;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Destination getDestination() {
		return destination;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	
}
