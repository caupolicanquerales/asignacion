package com.capo.redisVersion2.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.capo.redisVersion2.dto.CostAndDestination;
import com.capo.redisVersion2.dto.Destinations;
import com.capo.redisVersion2.entity.DestinationPointOfSalesMongo;
import com.capo.redisVersion2.interfaces.CostAndRouteRedis;
import com.capo.redisVersion2.interfaces.RecoverFileFromResource;
import com.capo.redisVersion2.repository.DestinationPointOfSalesMongoRepository;
import com.capo.redisVersion2.request.VertexRedisRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PreloadDestinationAndCost implements CommandLineRunner{
	
	@Autowired
	DestinationPointOfSalesMongoRepository destinationPointOfSales;
	
	@Autowired
	CostAndRouteRedis costAndRouteRedis;
	
	@Autowired
	RecoverFileFromResource recoverFile;
	
	private static final String DESTINATIONS="destinations";
	private static final String FILE_COSTS="/information/cost_and_destination_point_of_sales.json";

	
	@Override
	public void run(String... args) throws Exception {
		for(String arg: args) {
			if(arg.equals(DESTINATIONS)) {
				System.out.println("Ejecute el command line in DESTINATIONS");
				Destinations destinations= getFileFromResourceCostAndDestination(FILE_COSTS);
				destinationPointOfSales.findAll().hasElements().map(elements->{
					if(elements) {
						return destinationPointOfSales.findAll()
							.map(this::savingDestinationInRedis).subscribe();
					}else {
						return Flux.fromIterable(destinations.getCostAndDestination())
								.map(this::getDestinationPointOfSalesMongo)
								.flatMap(destination->destinationPointOfSales.save(destination))
								.map(this::savingDestinationInRedis).subscribe();
					}
				}).subscribe();
			}
		}
	}
	
	
	private Destinations getFileFromResourceCostAndDestination(String address) throws Exception{
		String json= recoverFile.getFileInString(address);
		ObjectMapper mapper = new ObjectMapper();
		Destinations destinations= mapper.readValue(json, Destinations.class);
		return destinations;
	}
	private DestinationPointOfSalesMongo getDestinationPointOfSalesMongo(CostAndDestination destination) {
		DestinationPointOfSalesMongo destinationMongo = new DestinationPointOfSalesMongo();
		destinationMongo.setCostAndDestination(destination);
		return destinationMongo;
	}
	
	private Mono<String> savingDestinationInRedis(DestinationPointOfSalesMongo response) {
		VertexRedisRequest vertexRedis= new VertexRedisRequest();
		vertexRedis.setStartVertex(response.getCostAndDestination().getStartVertex());
		vertexRedis.setEndVertex(response.getCostAndDestination().getEndVertex());
		vertexRedis.setCost(response.getCostAndDestination().getCost());
		costAndRouteRedis.saveAndUpdateCostAndDestinationStartingApp(vertexRedis);
		return Mono.just("OK");
	}
}
