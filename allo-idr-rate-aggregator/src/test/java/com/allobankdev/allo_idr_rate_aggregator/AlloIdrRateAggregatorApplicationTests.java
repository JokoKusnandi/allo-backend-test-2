package com.allobankdev.allo_idr_rate_aggregator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
  properties = {
    "spring.data.mongodb.auto-index-creation=false",
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration"
  }
)
class AlloIdrRateAggregatorApplicationTests {

	@Test
	void contextLoads() {
	}

}
