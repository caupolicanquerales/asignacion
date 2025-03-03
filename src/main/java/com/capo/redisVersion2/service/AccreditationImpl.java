package com.capo.redisVersion2.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capo.redisVersion2.dto.Point;
import com.capo.redisVersion2.entity.AccreditationMongo;
import com.capo.redisVersion2.interfaces.Accreditation;
import com.capo.redisVersion2.interfaces.CostAndRouteRedis;
import com.capo.redisVersion2.interfaces.PointsOfSaleRedis;
import com.capo.redisVersion2.repository.AccreditationMongoRepository;
import com.capo.redisVersion2.request.AccreditationRequest;

import reactor.core.publisher.Mono;

@Service
public class AccreditationImpl implements Accreditation {
	
	@Autowired
	CostAndRouteRedis costAndRouteRedis;
	
	@Autowired
	PointsOfSaleRedis pointsOfSaleRedis;
	
	@Autowired
	AccreditationMongoRepository accreditationRepository;
	
	@Override
	public Mono<String> createAccreditation(AccreditationRequest request) {
		return costAndRouteRedis.estimationOfCosts(request.getTravelfrom())
				.map(result->result.getResult())
				.map(mapResult->{
					Map<Integer,String> mapWeight= mapResult.get("weight");
					Map<Integer,String> mapRoute= mapResult.get("route");
					Integer travelTo = Integer.valueOf(request.getTravelTo());
					if(mapWeight.get(travelTo)!=null){
						return getPointsOfSale(request.getTravelfrom(), mapWeight.get(travelTo), mapRoute.get(travelTo));
					}
					return Mono.just("ERROR");
				}).flatMap(res->res);
	}
	
	private Mono<String> getPointsOfSale(String travelFrom, String cost, String route) {
		return pointsOfSaleRedis.getPointsOfSale()
		.map(points-> points.getPoints().stream().filter(point->point.getLocation().equals(travelFrom))
				.collect(Collectors.toList()).get(0)
		).map(point->getAccreditationMongo(point, cost, route))
		.map(accreditationRepository::save)
		.flatMap(response->{
			return response.map(item->item.getId());
		}).map(result->{
			return "OK";
		});
	}
	
	private AccreditationMongo getAccreditationMongo(Point point, String cost, String route) {
		AccreditationMongo accreditationMongo= new AccreditationMongo();
		accreditationMongo.setCost(cost);
		accreditationMongo.setRoute(route);
		accreditationMongo.setDateOfReception(setCurrentDate());
		accreditationMongo.setIdPontOfSale(point.getLocation());
		accreditationMongo.setNamePontOfSale(point.getId());
		return accreditationMongo;
	}
	
	private String setCurrentDate() {
		SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy"); 
		return ft.format(new Date());
	}
}
