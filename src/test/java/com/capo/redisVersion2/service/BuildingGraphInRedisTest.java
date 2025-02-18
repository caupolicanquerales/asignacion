package com.capo.redisVersion2.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.redisson.api.RMapReactive;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.capo.redisVersion2.dto.GraphObject;
import com.capo.redisVersion2.interfaces.BasicPetitionRedis;

@ExtendWith(SpringExtension.class)
public class BuildingGraphInRedisTest {

	@InjectMocks
	private BuildingGraphRedisImpl buildingGraphRedis;

	@Mock
	private BasicPetitionRedis petitionRedis;

	private RMapReactive<String, String> map;

	@BeforeEach
	public void setup() {
		map = new MapReactiveMock();
	}

	@Test
	public void buildingGraph_test() {
		when(petitionRedis.getReactiveMap(Mockito.anyString())).thenReturn(map);
		when(petitionRedis.getReactiveMap(Mockito.anyString())).thenReturn(map);
		GraphObject result = buildingGraphRedis.buildingGraph().block();
		assert (result.getGraphObject().size() > 0);
	}

}
