package com.capo.redisVersion2.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.capo.redisVersion2.dto.Destination;
import com.capo.redisVersion2.entity.DestinationPointOfSalesMongo;

import reactor.core.publisher.Mono;

@Repository
public interface DestinationPointOfSalesMongoRepository extends ReactiveMongoRepository<DestinationPointOfSalesMongo, String>{
	Mono<DestinationPointOfSalesMongo> findByDestination(Destination costAndDestination);
	Mono<DestinationPointOfSalesMongo> deleteByDestination(Destination costAndDestination);
}
