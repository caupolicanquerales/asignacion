package com.capo.redisVersion2.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.capo.redisVersion2.dto.CostAndDestination;

@Document(collection="costs")
public class DestinationPointOfSalesMongo {
	
	@Id
	private String id;
	private CostAndDestination costAndDestination;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public CostAndDestination getCostAndDestination() {
		return costAndDestination;
	}
	public void setCostAndDestination(CostAndDestination costAndDestination) {
		this.costAndDestination = costAndDestination;
	}
}
