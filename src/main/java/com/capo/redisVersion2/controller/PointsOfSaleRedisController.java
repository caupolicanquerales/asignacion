package com.capo.redisVersion2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capo.redisVersion2.interfaces.PointsOfSaleRedis;
import com.capo.redisVersion2.request.PointsRedisRequest;
import com.capo.redisVersion2.response.ResponsePointsRedis;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("puntos")
public class PointsOfSaleRedisController {
	
	@Autowired
	PointsOfSaleRedis pointsOfSale;
	
	@PostMapping("/save")
	public Mono<ResponseEntity<String>> saveAndUpdatePointsOfsale(@RequestBody PointsRedisRequest request){
		return pointsOfSale.updateCostPointsOfSale(request)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@PostMapping("/remove")
	public Mono<ResponseEntity<String>> removePointsOfSale(@RequestBody PointsRedisRequest request){
		return pointsOfSale.removePointsOfSale(request)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@GetMapping("/points")
	public Mono<ResponseEntity<ResponsePointsRedis>> getPointsOfSale(@RequestBody PointsRedisRequest request){
		return pointsOfSale.getPointsOfSale()
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@PostMapping("/save-point")
	public Mono<ResponseEntity<String>> savePointsOfsale(@RequestBody PointsRedisRequest request){
		return pointsOfSale.savePointsOfSale(request)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
}
