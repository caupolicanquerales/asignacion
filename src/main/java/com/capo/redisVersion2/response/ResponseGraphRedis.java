package com.capo.redisVersion2.response;

import java.util.Map;

public class ResponseGraphRedis {
	private Map<String,Map<Integer,String>> result;

	public Map<String, Map<Integer, String>> getResult() {
		return result;
	}

	public void setResult(Map<String, Map<Integer, String>> result) {
		this.result = result;
	}
}
