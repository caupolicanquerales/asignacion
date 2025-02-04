package com.capo.redisVersion2.interfaces;

public interface ConvertToJson {
	<T> String convertToJson(T object) throws Exception;
	<T>  T convertJsonToObject(String json, Class<T> object) throws Exception;
}
