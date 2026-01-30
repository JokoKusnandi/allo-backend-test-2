package com.allobankdev.allo_idr_rate_aggregator.strategy;

import com.allobankdev.allo_idr_rate_aggregator.strategy.impl.SupportedCurrenciesFetcher;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"rawtypes", "unchecked"})
class SupportedCurrenciesFetcherTest {

    @Test
    void shouldFetchSupportedCurrencies() {
         // ---------- GIVEN ----------
        WebClient webClient = Mockito.mock(WebClient.class, Mockito.RETURNS_DEEP_STUBS);

        Map<String, String> response =
                Collections.singletonMap("USD", "US Dollar");

        Mockito.when(
                webClient.get()
                        .uri("/currencies")
                        .retrieve()
                        .bodyToMono(
                            Mockito.<ParameterizedTypeReference<Map<String, String>>>any()
                        )
        ).thenReturn(Mono.just(response));

        SupportedCurrenciesFetcher fetcher = new SupportedCurrenciesFetcher(webClient);

        // ---------- WHEN ----------
        List<Object> result = fetcher.fetch().block();
        // ---------- THEN ----------
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
       
    }
}

