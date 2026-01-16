package com.allobankdev.allo_idr_rate_aggregator.strategy;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;

import com.allobankdev.allo_idr_rate_aggregator.model.dto.FrankfurterLatestResponse;
import com.allobankdev.allo_idr_rate_aggregator.service.SpreadCalculator;
import com.allobankdev.allo_idr_rate_aggregator.strategy.impl.LatestIDRRatesFetcher;

import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LatestIDRRatesFetcherTest {

    @Test
    public void shouldCalculateUsdBuySpread() {
        // mock response
        FrankfurterLatestResponse response = new FrankfurterLatestResponse();
        response.setBase("IDR");
        response.setRates(Collections.singletonMap("USD", 0.000064));

        WebClient webClient = Mockito.mock(WebClient.class, Mockito.RETURNS_DEEP_STUBS);

        Mockito.when(
                webClient.get()
                        .uri("/latest?base=IDR")
                        .retrieve()
                        .bodyToMono(FrankfurterLatestResponse.class)
        ).thenReturn(Mono.just(response));

        SpreadCalculator spreadCalculator = Mockito.mock(SpreadCalculator.class);
        Mockito.when(spreadCalculator.spreadFactor()).thenReturn(0.005);

        LatestIDRRatesFetcher fetcher = new LatestIDRRatesFetcher(webClient, spreadCalculator);

        // var result = fetcher.fetch().block();
           // WHEN
        List<Object> result = fetcher.fetch().block();

         // ---------- THEN ----------
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) result.get(0);

        assertThat(data)
                .containsKey("USD_BuySpread_IDR")
                .containsKey("rates")
                .containsEntry("base", "IDR");
    }
}

