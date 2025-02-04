package com.capo.redisVersion2.dto;

import java.util.List;
import java.util.Map;

public class GraphObject {
	
	private Map<Integer,List<Node>> graphObject;

	public Map<Integer, List<Node>> getGraphObject() {
		return graphObject;
	}

	public void setGraphObject(Map<Integer, List<Node>> graphObject) {
		this.graphObject = graphObject;
	}
}
