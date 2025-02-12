package com.capo.redisVersion2.enums;

public enum RedisEnum {
	MAP_COST("costos"),
	MAP_STORES("puntos"),
	GRAPH("graph");
	
	public final String value;
	
	RedisEnum(String value) {
		this.value=value;
	}
}
