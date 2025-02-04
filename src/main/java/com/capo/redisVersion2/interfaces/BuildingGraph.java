package com.capo.redisVersion2.interfaces;

import java.util.List;
import java.util.Map;

import com.capo.redisVersion2.dto.Node;

public interface BuildingGraph {
	Map<Integer,List<Node>> buildingGraph();
}
