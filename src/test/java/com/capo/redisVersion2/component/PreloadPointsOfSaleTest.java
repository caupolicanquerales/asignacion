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

import com.capo.redisVersion2.dto.Point;
import com.capo.redisVersion2.entity.PointOfSalesInMongo;
import com.capo.redisVersion2.interfaces.PointsOfSaleRedis;
import com.capo.redisVersion2.interfaces.RecoverFileFromResource;
import com.capo.redisVersion2.repository.PointOfSaleMongoRepository;
import com.capo.redisVersion2.service.RecoverFileFromResourceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
public class PreloadPointsOfSaleTest {
	
	@InjectMocks
	PreloadPointsOfSale preloadPointsOfSale;
	
	@Mock
	PointOfSaleMongoRepository pointOfSaleMongo;
	
	@Mock
	PointsOfSaleRedis pointsOfSaleRedis;
	
	@Spy
	RecoverFileFromResource recoverFile =new RecoverFileFromResourceImpl();
	
	private String[] customArgs;
	private PointOfSalesInMongo pointOfSalesInMongo;
	
	@BeforeEach
	public void setup() {
		customArgs = new String[] {"points","destinations"};
		pointOfSalesInMongo= new PointOfSalesInMongo();
		pointOfSalesInMongo.setPoint(new Point());
	}
	
	@Test
	public void runEmptyDataInMongo_test() throws Exception {
		when(pointOfSaleMongo.findAll()).thenReturn(Flux.empty());
		when(pointOfSaleMongo.save(Mockito.any())).thenReturn(Mono.just(pointOfSalesInMongo));
		when(pointsOfSaleRedis.savePointsOfSaleStartingApp(Mockito.any())).thenReturn("OK");
		preloadPointsOfSale.run(customArgs);
		verify(pointsOfSaleRedis,atLeast(1)).savePointsOfSaleStartingApp(Mockito.any());
	}
	
	@Test
	void runWithDataInMongo_test() throws Exception {
		when(pointOfSaleMongo.findAll()).thenReturn(Flux.just(pointOfSalesInMongo,pointOfSalesInMongo))
		.thenReturn(Flux.just(pointOfSalesInMongo,pointOfSalesInMongo));
		when(pointsOfSaleRedis.savePointsOfSaleStartingApp(Mockito.any())).thenReturn("OK");
		preloadPointsOfSale.run(customArgs);
		verify(pointsOfSaleRedis,atLeast(1)).savePointsOfSaleStartingApp(Mockito.any());
	}
}
