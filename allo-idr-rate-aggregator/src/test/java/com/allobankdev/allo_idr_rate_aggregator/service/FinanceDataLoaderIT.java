package com.allobankdev.allo_idr_rate_aggregator.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FinanceDataLoaderIT {

    @Autowired
    private FinanceDataStore store;

    @Test
    void shouldLoadDataOnStartup() {
        var data = store.get("latest_idr_rates").block();
        assertThat(data).isNotNull();
    }
}

