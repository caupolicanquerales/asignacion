package com.capo.redisVersion2.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BuildingGraphInRedisTest {
	
	@Autowired
	BuildingGraphImpl buildingGraphInRedis;
	
	@Test
	public void buildingGraphTest() {
		buildingGraphInRedis.buildingGraph();
	}
}
