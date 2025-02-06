package com.capo.redisVersion2.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capo.redisVersion2.dto.PointsOfSale;
import com.capo.redisVersion2.interfaces.PointsOfSaleRedis;
import com.capo.redisVersion2.request.RequestPointsRedis;
import com.capo.redisVersion2.response.ResponsePointsRedis;

import reactor.core.publisher.Mono;

@Service
public class PointsOfSaleRedisImpl implements PointsOfSaleRedis{
	
	@Autowired
	private RedissonReactiveClient client;
	
	private final static String MAP_POINT = "puntos";
	
	@Override
	public Mono<String> saveAndUpdateCostPointsOfSale(RequestPointsRedis request) {
		RMapReactive<String,String> map =  client.getMap(MAP_POINT, StringCodec.INSTANCE);
		map.put(request.getLocation(),request.getId()).then().subscribe();
		return Mono.just("OK");
	}
	
	@Override
	public Mono<String> removePointsOfSale(RequestPointsRedis request) {
		RMapReactive<String,String> map =  client.getMap(MAP_POINT, StringCodec.INSTANCE);
		map.remove(request.getLocation()).then().subscribe();
		return Mono.just("OK");
	}
	
	@Override
	public Mono<ResponsePointsRedis> getPointsOfSale() {
		RMapReactive<String,String> map =  client.getMap(MAP_POINT, StringCodec.INSTANCE);
		return map.entryIterator().map(this::getPointsOfSale)
			.collect(Collectors.toList())
			.map(this::getResponsePointsRedis);
	}
	
	private PointsOfSale getPointsOfSale(Map.Entry<String, String> entry) {
		PointsOfSale point = new PointsOfSale();
		point.setId(entry.getKey());
		point.setLocation(entry.getValue());
		return point;
	}
	
	private ResponsePointsRedis getResponsePointsRedis(List<PointsOfSale> list) {
		ResponsePointsRedis points = new ResponsePointsRedis();
		points.setPoints(list);
		return points;
	}
}
