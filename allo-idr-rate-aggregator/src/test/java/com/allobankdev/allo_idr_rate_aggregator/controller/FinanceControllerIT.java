package com.allobankdev.allo_idr_rate_aggregator.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class FinanceControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnLatestIdrRates() {
        webTestClient.get()
                .uri("/api/finance/data/latest_idr_rates")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray();
    }
}

