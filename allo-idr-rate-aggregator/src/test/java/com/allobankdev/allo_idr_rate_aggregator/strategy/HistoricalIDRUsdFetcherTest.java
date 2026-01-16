package com.allobankdev.allo_idr_rate_aggregator.strategy;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;

import com.allobankdev.allo_idr_rate_aggregator.strategy.impl.HistoricalIDRUsdFetcher;

import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HistoricalIDRUsdFetcherTest {

    @Test
    void shouldFetchHistoricalRates() {
        WebClient webClient = Mockito.mock(WebClient.class, Mockito.RETURNS_DEEP_STUBS);

        Mockito.when(
                webClient.get()
                        .uri("/2024-01-01..2024-01-05?from=IDR&to=USD")
                        .retrieve()
                        .bodyToMono(Map.class)
        ).thenReturn(Mono.just(Collections.singletonMap("USD", "OK")));

        HistoricalIDRUsdFetcher fetcher =
                new HistoricalIDRUsdFetcher(webClient);

        var result = fetcher.fetch().block();
        assertThat(result).isNotEmpty();
    }
}

