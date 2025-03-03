package com.capo.redisVersion2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capo.redisVersion2.interfaces.CostAndRouteRedis;
import com.capo.redisVersion2.request.VertexRedisRequest;
import com.capo.redisVersion2.response.ResponseCostDestinations;
import com.capo.redisVersion2.response.ResponseGraphRedis;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("costos")
public class CostPointsOfSaleRedisController {
	
	@Autowired
	CostAndRouteRedis costAndRoute;
	
	@PostMapping("/update-cost")
	public Mono<ResponseEntity<String>> updateCostAndDestination(@RequestBody VertexRedisRequest request){
		return costAndRoute.updateCost(request)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@DeleteMapping("/delete")
	public Mono<ResponseEntity<String>> deleteCostAndDestination(@RequestBody VertexRedisRequest request){
		return costAndRoute.deleteCostAndDestination(request)
				.map(ResponseEntity.status(HttpStatus.OK)::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@GetMapping("/prices")
	public Mono<ResponseEntity<ResponseGraphRedis>> getPricesFromVertex(@RequestBody VertexRedisRequest request){
		return costAndRoute.estimationOfCosts(request.getStartVertex())
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@GetMapping("/build")
	public Mono<ResponseEntity<String>> buildingGraph(@RequestBody VertexRedisRequest request){
		return costAndRoute.buildingGraph()
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@GetMapping("/destinations")
	public Mono<ResponseEntity<ResponseCostDestinations>> getAllCostsAndDestinations(@RequestBody VertexRedisRequest request){
		return costAndRoute.getAllCostsAndDestinations()
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@PostMapping("/save-destination")
	public Mono<ResponseEntity<String>> saveCostAndDestination(@RequestBody VertexRedisRequest request){
		return costAndRoute.saveCostAndDestination(request)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
}
