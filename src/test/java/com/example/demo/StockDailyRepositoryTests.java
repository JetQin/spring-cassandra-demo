package com.example.demo;

import com.example.demo.repo.StockDaily;
import com.example.demo.service.StockDailyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class StockDailyRepositoryTests {

	@Autowired
	StockDailyRepository repository;

	@BeforeEach
	public void setup() {
		log.info("Setup test");
		repository.deleteAll();
		Assert.assertEquals(0L, repository.count());
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

		repository.save(stock);
		Assert.assertEquals(1L, repository.count());

	}

	@AfterEach
	public void tearDown(){
		log.info("cleanup test");
		repository.deleteAll();
		Assert.assertEquals(0L, repository.count());
	}
}
