package com.capo.redisVersion2.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.redisson.api.RMapReactive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capo.redisVersion2.dto.Point;
import com.capo.redisVersion2.enums.RedisEnum;
import com.capo.redisVersion2.interfaces.BasicPetitionRedis;
import com.capo.redisVersion2.interfaces.PointsOfSaleRedis;
import com.capo.redisVersion2.request.PointsRedisRequest;
import com.capo.redisVersion2.response.ResponsePointsRedis;

import reactor.core.publisher.Mono;

@Service
public class PointsOfSaleRedisImpl implements PointsOfSaleRedis{
	
	@Autowired
	private BasicPetitionRedis petitionRedis;
	
	@Override
	public Mono<String> saveAndUpdateCostPointsOfSale(PointsRedisRequest request) {
		RMapReactive<String,String> map = this.petitionRedis.getReactiveMap(RedisEnum.MAP_STORES.value);
		map.put(request.getLocation(),request.getId()).then().subscribe();
		return Mono.just("OK");
	}
	
	@Override
	public Mono<String> removePointsOfSale(PointsRedisRequest request) {
		RMapReactive<String,String> map = this.petitionRedis.getReactiveMap(RedisEnum.MAP_STORES.value);
		map.remove(request.getLocation()).then().subscribe();
		return Mono.just("OK");
	}
	
	@Override
	public Mono<ResponsePointsRedis> getPointsOfSale() {
		RMapReactive<String,String> map = this.petitionRedis.getReactiveMap(RedisEnum.MAP_STORES.value);
		return map.entryIterator().map(this::getPointsOfSale)
			.collect(Collectors.toList())
			.map(this::getResponsePointsRedis);
	}
	
	@Override
	public String savePointsOfSaleStartingApp(PointsRedisRequest request) {
		RMapReactive<String,String> map = this.petitionRedis.getReactiveMap(RedisEnum.MAP_STORES.value);
		map.put(request.getLocation(),request.getId()).then().subscribe();
		return "OK";
	}
	
	private Point getPointsOfSale(Map.Entry<String, String> entry) {
		Point point = new Point();
		point.setId(entry.getKey());
		point.setLocation(entry.getValue());
		return point;
	}
	
	private ResponsePointsRedis getResponsePointsRedis(List<Point> list) {
		ResponsePointsRedis points = new ResponsePointsRedis();
		points.setPoints(list);
		return points;
	}
}
