package com.example.demo;

import com.example.demo.repo.StockDaily;
import com.example.demo.service.ReactiveStockDailyRepository;
import com.example.demo.service.StockDailyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class ReactiveStockDailyRepositoryTests {

	@Autowired
	ReactiveStockDailyRepository repository;

	@BeforeEach
	public void setup() {
		log.info("Setup test");

		Mono<Long> countOfStock = repository.deleteAll()
						.then(repository.count());
		StepVerifier.create(countOfStock).expectNext(0L).verifyComplete();

	}


	@Test
	public void testShouldSaveStock(){
		StockDaily stock = new StockDaily(
				LocalDate.now(),
				"1300263",
				"999999",
				new BigDecimal(0),
				new BigDecimal(4.51),
				new BigDecimal(0.0057303629556702),
				new BigDecimal(0.61613453601859),
				new BigDecimal(4.43),
				new BigDecimal(2993858485.53),
				new BigDecimal(0.1251),
				"平安水务",
				new BigDecimal(4.47),
				new BigDecimal(35.731414868106),
				new BigDecimal(0.002242),
				new BigDecimal(4.47),
				"平安水务",
				new BigDecimal(2993858485.53),
				new BigDecimal(17299161.92),
				new BigDecimal(0.01),
				new BigDecimal(3868508),
				new BigDecimal(0.087285532329362),
				new BigDecimal(4.46),
				new BigDecimal(0.01793721973094)
		);


		Mono<Long> countOfStock = repository.save(stock).then(repository.count());
		StepVerifier.create(countOfStock).expectNext(1L).verifyComplete();

	}

//	@AfterEach
//	public void tearDown(){
//		log.info("cleanup test");
//		Mono<Long> count = repository.deleteAll().then(repository.count());
//        StepVerifier.create(count).expectNext(0L).verifyComplete();
//
//	}
}
