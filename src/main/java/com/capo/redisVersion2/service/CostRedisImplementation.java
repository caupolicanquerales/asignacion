package com.capo.redisVersion2.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.redisson.api.RMapReactive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capo.redisVersion2.dto.Destination;
import com.capo.redisVersion2.dto.GraphObject;
import com.capo.redisVersion2.entity.DestinationPointOfSalesMongo;
import com.capo.redisVersion2.enums.RedisEnum;
import com.capo.redisVersion2.interfaces.BasicPetitionRedis;
import com.capo.redisVersion2.interfaces.BuildingGraphRedis;
import com.capo.redisVersion2.interfaces.CostAndRouteRedis;
import com.capo.redisVersion2.interfaces.DijkstraAlgorithmRedis;
import com.capo.redisVersion2.repository.DestinationPointOfSalesMongoRepository;
import com.capo.redisVersion2.request.VertexRedisRequest;
import com.capo.redisVersion2.response.ResponseCostDestinations;
import com.capo.redisVersion2.response.ResponseGraphRedis;

import reactor.core.publisher.Mono;

@Service
public class CostRedisImplementation implements CostAndRouteRedis {
	
	@Autowired
	private BuildingGraphRedis buildingGraph;
	
	@Autowired
	private DijkstraAlgorithmRedis dijkstraAlgorithm;
	
	@Autowired
	private BasicPetitionRedis petitionRedis;
	
	@Autowired
	private DestinationPointOfSalesMongoRepository destinationPointOfSales;
	
	@Override
	public Mono<String> buildingGraph(){
		return buildingGraph.buildingGraph()
				.map(this::saveGraphInRedis);
	}
	
	@Override
	public Mono<String> updateCost(VertexRedisRequest request) {
		String key= request.getStartVertex()+","+request.getEndVertex();
		RMapReactive<String,String> map = this.petitionRedis.getReactiveMap(RedisEnum.MAP_COST.value);
		return Mono.just(map.get(key)).flatMap(item->item)
				.hasElement().map(element->{
					if(element) {
						Mono<String> cost= map.get(key);
						return updateCostInMongo(request,cost).map(resut->{
							map.put(key, request.getCost()).then().subscribe();
							return "OK";
						});
					}
					return Mono.just("ERROR");
				}).flatMap(result->result);
	}
	
	@Override
	public Mono<String> deleteCostAndDestination(VertexRedisRequest request) {
		String key= request.getStartVertex()+","+request.getEndVertex();
		RMapReactive<String,String> map = this.petitionRedis.getReactiveMap(RedisEnum.MAP_COST.value);
		return Mono.just(map.get(key)).flatMap(item->item)
		.hasElement().map(element->{
			if(element) {
				map.remove(key).then().subscribe();
				Destination destination= getCostAndDestination(request);
				return deleteCostAndDestinationInMongo(destination);
			}
			return Mono.just("ERROR");
		}).flatMap(result->result);
	}
	
	@Override
	public Mono<ResponseGraphRedis> estimationOfCosts(String vertex) {
		return this.petitionRedis.getReactiveJsonBucket(RedisEnum.GRAPH.value)
			.map(graph->dijkstraAlgorithm.dijkstra(graph.getGraphObject(), Integer.valueOf(vertex)))
			.map(this::getResponseGraph);
	}
	
	@Override
	public String saveAndUpdateCostAndDestinationStartingApp(VertexRedisRequest request) {
		String key= request.getStartVertex()+","+request.getEndVertex();
		RMapReactive<String,String> map = this.petitionRedis.getReactiveMap(RedisEnum.MAP_COST.value);
		map.put(key, request.getCost()).then().subscribe();
		return "OK";
	}
	
	@Override
	public Mono<ResponseCostDestinations> getAllCostsAndDestinations() {
		RMapReactive<String,String> map = this.petitionRedis.getReactiveMap(RedisEnum.MAP_COST.value);
		return map.entryIterator().map(this::getCostAndDestination)
			.collect(Collectors.toList())
			.map(this::getResponseCostDestinations);
	}
	
	
	@Override
	public Mono<String> saveCostAndDestination(VertexRedisRequest request) {
		String key= request.getStartVertex()+","+request.getEndVertex();
		RMapReactive<String,String> map = this.petitionRedis.getReactiveMap(RedisEnum.MAP_COST.value);
		return Mono.just(map.get(key)).flatMap(item->item)
		.hasElement().map(element->{
			if(!element) {
				map.put(key, request.getCost()).then().subscribe();
				return saveCostAndDestinationInMongo(request);
			}
			return Mono.just("ERROR");
		}).flatMap(result->result);
	}
	
	private Mono<String> saveCostAndDestinationInMongo(VertexRedisRequest request) {
		Destination costAndDestination = getCostAndDestination(request);
		DestinationPointOfSalesMongo destination =getDestinationPointOfSalesMongo(costAndDestination);
		return destinationPointOfSales.save(destination).map(result->{
			return "OK";
		});
	}
	
	private Mono<String> updateCostInMongo(VertexRedisRequest request,Mono<String> cost) {
		return cost.map(this::setCostInDestination)
				.map(destination->setDestinationForUpdate(destination,request))
				.map(destinationPointOfSales::findByDestination)
				.map(result->setCostInDestinationPointOfSalesMongo(result,request))
				.flatMap(this::updateCostInColectionMongo);
	}
	
	private Mono<String> deleteCostAndDestinationInMongo(Destination destination) {
		return destinationPointOfSales.findByDestination(destination)
				.map(collec->{
					return destinationPointOfSales.deleteById(collec.getId());
				})
				.map(result->{
					return "OK";
				});
	}
	
	
	private DestinationPointOfSalesMongo getDestinationPointOfSalesMongo(Destination destination) {
		DestinationPointOfSalesMongo destinationMongo = new DestinationPointOfSalesMongo();
		destinationMongo.setDestination(destination);
		return destinationMongo;
	}
	
	private Destination getCostAndDestination(VertexRedisRequest request) {
		Destination costAndDestination = new Destination();
		costAndDestination.setCost(request.getCost());
		costAndDestination.setStartVertex(request.getStartVertex());
		costAndDestination.setEndVertex(request.getEndVertex());
		return costAndDestination;
	}
	
	private Destination setCostInDestination(String cost) {
		Destination costAndDestination = new Destination();
		costAndDestination.setCost(cost);
		return costAndDestination;
	}
	
	private Mono<DestinationPointOfSalesMongo> setCostInDestinationPointOfSalesMongo(Mono<DestinationPointOfSalesMongo> destinationMongo,VertexRedisRequest request) {
		return destinationMongo.map(destination->{
			destination.getDestination().setCost(request.getCost());
			return destination;
		});
	}
	
	private Mono<String> updateCostInColectionMongo(Mono<DestinationPointOfSalesMongo> destinationMongo) {
		return destinationMongo.flatMap(destinationPointOfSales::save)
				.map(result->{
					return "OK";
				});
	}
	
	private Destination setDestinationForUpdate(Destination costAndDestination,VertexRedisRequest request) {
		costAndDestination.setStartVertex(request.getStartVertex());
		costAndDestination.setEndVertex(request.getEndVertex());
		return costAndDestination;
	}
	
	private VertexRedisRequest getCostAndDestination(Map.Entry<String, String> entry) {
		VertexRedisRequest destination = new VertexRedisRequest();
		String[] vertex= entry.getKey().split(","); 
		destination.setCost(entry.getValue());
		destination.setStartVertex(vertex[0]);
		destination.setEndVertex(vertex[1]);
		return destination;
	}
	
	private ResponseCostDestinations getResponseCostDestinations(List<VertexRedisRequest> list) {
		ResponseCostDestinations points = new ResponseCostDestinations();
		points.setCostAndDestination(list);
		return points;
	}
	
	private ResponseGraphRedis getResponseGraph(Map<String,Map<Integer,String>> resultDij) {
		ResponseGraphRedis result = new ResponseGraphRedis();
		result.setResult(resultDij);
		return result;
	}
	
	private String saveGraphInRedis(GraphObject graph){
		return  this.petitionRedis.saveReactiveJsonBucket(RedisEnum.GRAPH.value, graph);
	}
	
}
