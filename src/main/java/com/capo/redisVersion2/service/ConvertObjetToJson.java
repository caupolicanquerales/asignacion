package com.capo.redisVersion2.service;

import org.springframework.stereotype.Service;

import com.capo.redisVersion2.interfaces.ConvertToJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConvertObjetToJson implements ConvertToJson {
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public <T> String convertToJson(T object) throws Exception{
		try {
			String json= mapper.writeValueAsString(object);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new Exception("Error");
		}
	}
	
	@Override
	public <T>  T convertJsonToObject(String json, Class<T> object) throws Exception{
		try {
			return mapper.readValue(json, object);
		} catch (JsonProcessingException e) {
			throw new Exception("Error");
		}
	}
}
