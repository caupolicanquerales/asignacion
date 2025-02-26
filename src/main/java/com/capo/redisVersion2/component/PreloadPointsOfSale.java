package com.capo.redisVersion2.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.capo.redisVersion2.dto.Point;
import com.capo.redisVersion2.dto.PointOfSales;
import com.capo.redisVersion2.entity.PointOfSalesInMongo;
import com.capo.redisVersion2.interfaces.PointsOfSaleRedis;
import com.capo.redisVersion2.interfaces.RecoverFileFromResource;
import com.capo.redisVersion2.repository.PointOfSaleMongoRepository;
import com.capo.redisVersion2.request.PointsRedisRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PreloadPointsOfSale implements CommandLineRunner {
	
	@Autowired
	PointOfSaleMongoRepository pointOfSaleMongo;
	
	@Autowired
	PointsOfSaleRedis pointsOfSaleRedis;
	
	@Autowired
	RecoverFileFromResource recoverFile;
	
	private static final String POINTS="points";
	private static final String FILE_POINTS="/information/point_of_sales.json";
	
	@Override
	public void run(String... args) throws Exception {
		for(String arg: args) {
			if(arg.equals(POINTS)) {
				PointOfSales pointOfSales= getFileFromResourcePointOfSales(FILE_POINTS);
				pointOfSaleMongo.findAll().hasElements().map(elements->{
					if(elements) {
						return pointOfSaleMongo.findAll()
								.map(this::savingPointOfSalesInRedis).subscribe();
					}else {
						return Flux.fromIterable(pointOfSales.getPointOfSales())
								.map(this::getPointOfSalesInMongo)
								.flatMap(destination->pointOfSaleMongo.save(destination))
								.map(this::savingPointOfSalesInRedis).subscribe();
					}
				}).subscribe();
			}
		}
	}
	
	private PointOfSales getFileFromResourcePointOfSales(String address) throws Exception{
		String json= recoverFile.getFileInString(address);
		ObjectMapper mapper = new ObjectMapper();
		PointOfSales points= mapper.readValue(json, PointOfSales.class);
		return points;
	}
	
	private PointOfSalesInMongo getPointOfSalesInMongo(Point point) {
		PointOfSalesInMongo pointsInMongo= new PointOfSalesInMongo();
		pointsInMongo.setPoint(point);
		return pointsInMongo;
	}
	private Mono<String> savingPointOfSalesInRedis(PointOfSalesInMongo response) {
		PointsRedisRequest pointsRedis= new PointsRedisRequest();
		pointsRedis.setId(response.getPoint().getId());
		pointsRedis.setLocation(response.getPoint().getLocation());
		pointsOfSaleRedis.savePointsOfSaleStartingApp(pointsRedis);
		return Mono.just("OK");
	}
}
