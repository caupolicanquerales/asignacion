package com.capo.redisVersion2.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.capo.redisVersion2.entity.DestinationPointOfSalesMongo;

@Repository
public interface DestinationPointOfSalesMongoRepository extends ReactiveMongoRepository<DestinationPointOfSalesMongo, String>{
	
}
