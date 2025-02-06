package com.capo.redisVersion2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capo.redisVersion2.interfaces.CostAndRouteRedis;
import com.capo.redisVersion2.request.RequestVertexRedis;
import com.capo.redisVersion2.response.ResponseGraphRedis;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("costos")
public class CostosEntrePuntosRedisController {
	
	@Autowired
	CostAndRouteRedis costAndRoute;
	
	@PostMapping("/save")
	public Mono<ResponseEntity<String>> saveCostAndDestination(@RequestBody RequestVertexRedis request){
		return costAndRoute.saveAndUpdateCostAndDestination(request)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@PostMapping("/delete")
	public Mono<ResponseEntity<String>> deleteCostAndDestination(@RequestBody RequestVertexRedis request){
		return costAndRoute.removeCostAndDestination(request)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@GetMapping("/prices")
	public Mono<ResponseEntity<ResponseGraphRedis>> getPricesFromVertex(@RequestBody RequestVertexRedis request){
		return costAndRoute.estimationOfCosts(request.getStartVertex())
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@GetMapping("/build")
	public Mono<ResponseEntity<String>> buildingGraph(@RequestBody RequestVertexRedis request){
		return costAndRoute.buildingGraph()
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
}
