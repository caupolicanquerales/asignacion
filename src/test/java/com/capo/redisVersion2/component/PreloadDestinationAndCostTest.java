package com.capo.redisVersion2.component;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.capo.redisVersion2.dto.Destination;
import com.capo.redisVersion2.entity.DestinationPointOfSalesMongo;
import com.capo.redisVersion2.interfaces.CostAndRouteRedis;
import com.capo.redisVersion2.interfaces.RecoverFileFromResource;
import com.capo.redisVersion2.repository.DestinationPointOfSalesMongoRepository;
import com.capo.redisVersion2.service.RecoverFileFromResourceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
class PreloadDestinationAndCostTest {
	
	@InjectMocks
	PreloadDestinationAndCost preloadDestination;
	
	@Mock
	DestinationPointOfSalesMongoRepository destinationPointOfSales;
	
	@Mock
	CostAndRouteRedis costAndRouteRedis;
	
	@Spy
	RecoverFileFromResource recoverFile =new RecoverFileFromResourceImpl();
	
	private String[] customArgs;
	private DestinationPointOfSalesMongo destinationMongo;
	
	@BeforeEach
	public void setup() {
		customArgs = new String[] {"points","destinations"};
		destinationMongo = new DestinationPointOfSalesMongo();
		destinationMongo.setDestination(new Destination());
	}
	
	@Test
	void runEmptyDataInMongo_test() throws Exception {
		when(destinationPointOfSales.findAll()).thenReturn(Flux.empty());
		when(destinationPointOfSales.save(Mockito.any())).thenReturn(Mono.just(destinationMongo));
		when(costAndRouteRedis.saveAndUpdateCostAndDestinationStartingApp(Mockito.any())).thenReturn("OK");
		preloadDestination.run(customArgs);
		verify(costAndRouteRedis,atLeast(1)).saveAndUpdateCostAndDestinationStartingApp(Mockito.any());
	}

	@Test
	void runWithDataInMongo_test() throws Exception {
		when(destinationPointOfSales.findAll()).thenReturn(Flux.just(destinationMongo,destinationMongo))
		.thenReturn(Flux.just(destinationMongo,destinationMongo));
		when(costAndRouteRedis.saveAndUpdateCostAndDestinationStartingApp(Mockito.any())).thenReturn("OK");
		preloadDestination.run(customArgs);
		verify(costAndRouteRedis,atLeast(1)).saveAndUpdateCostAndDestinationStartingApp(Mockito.any());
	}

}
