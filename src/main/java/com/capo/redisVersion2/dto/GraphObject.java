package com.capo.redisVersion2.dto;

import java.util.List;
import java.util.Map;

public class GraphObject {
	
	private Map<String,List<Node>> graphObject;

	public Map<String, List<Node>> getGraphObject() {
		return graphObject;
	}

	public void setGraphObject(Map<String, List<Node>> graphObject) {
		this.graphObject = graphObject;
	}
}
