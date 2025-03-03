package com.capo.redisVersion2.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="accreditation")
public class AccreditationMongo {
	
	@Id
	private String id;
	private String cost;
	private String idPontOfSale;
	private String dateOfReception;
	private String namePontOfSale;
	private String route;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getIdPontOfSale() {
		return idPontOfSale;
	}
	public void setIdPontOfSale(String idPontOfSale) {
		this.idPontOfSale = idPontOfSale;
	}
	public String getDateOfReception() {
		return dateOfReception;
	}
	public void setDateOfReception(String dateOfReception) {
		this.dateOfReception = dateOfReception;
	}
	public String getNamePontOfSale() {
		return namePontOfSale;
	}
	public void setNamePontOfSale(String namePontOfSale) {
		this.namePontOfSale = namePontOfSale;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
}
