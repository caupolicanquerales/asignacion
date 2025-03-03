package com.capo.redisVersion2.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.redisson.api.RMapReactive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capo.redisVersion2.dto.Point;
import com.capo.redisVersion2.entity.PointOfSalesInMongo;
import com.capo.redisVersion2.enums.RedisEnum;
import com.capo.redisVersion2.interfaces.BasicPetitionRedis;
import com.capo.redisVersion2.interfaces.PointsOfSaleRedis;
import com.capo.redisVersion2.repository.PointOfSaleMongoRepository;
import com.capo.redisVersion2.request.PointsRedisRequest;
import com.capo.redisVersion2.response.ResponsePointsRedis;

import reactor.core.publisher.Mono;

@Service
public class PointsOfSaleRedisImpl implements PointsOfSaleRedis{
	
	@Autowired
	private BasicPetitionRedis petitionRedis;
	
	@Autowired
	private PointOfSaleMongoRepository pointOfSaleMongo;
	
	@Override
	public Mono<String> updateCostPointsOfSale(PointsRedisRequest request) {
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
	
	@Override
	public Mono<String> savePointsOfSale(PointsRedisRequest request) {
		RMapReactive <String,String> map = this.petitionRedis.getReactiveMap(RedisEnum.MAP_STORES.value);
		return Mono.just(map.get(request.getLocation())).flatMap(item->item)
			.hasElement().map(element->{
				if(!element) {
					map.put(request.getLocation(),request.getId()).then().subscribe();
					return savePointInMongo(request);
				}
				return Mono.just("ERROR");
			}).flatMap(result->result);
	}
	
	private Mono<String> savePointInMongo(PointsRedisRequest request) {
		PointOfSalesInMongo pointOfSales =getPointOfSalesInMongo(request);
		return pointOfSaleMongo.save(pointOfSales).map(result->{
			return "OK";
		});
	}
	
	private PointOfSalesInMongo getPointOfSalesInMongo(PointsRedisRequest request) {
		PointOfSalesInMongo pointsInMongo= new PointOfSalesInMongo();
		Point point =getPoint(request.getId(), request.getLocation());
		pointsInMongo.setPoint(point);
		return pointsInMongo;
	}
	
	private Point getPointsOfSale(Map.Entry<String, String> entry) {
		return getPoint(entry.getKey(),entry.getValue());
	}
	
	private Point getPoint(String id, String location) {
		Point point = new Point();
		point.setId(id);
		point.setLocation(location);
		return point;
	}
	
	private ResponsePointsRedis getResponsePointsRedis(List<Point> list) {
		ResponsePointsRedis points = new ResponsePointsRedis();
		points.setPoints(list);
		return points;
	}
}
