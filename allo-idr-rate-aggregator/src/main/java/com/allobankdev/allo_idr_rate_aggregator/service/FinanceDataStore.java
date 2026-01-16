package com.allobankdev.allo_idr_rate_aggregator.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class FinanceDataStore {

    private volatile Map<String, List<Object>> store =
            Collections.emptyMap();

    public void initialize(Map<String, List<Object>> data) {
        this.store = Collections.unmodifiableMap(data);
    }

    public Mono<List<Object>> get(String resourceType) {
        return Mono.justOrEmpty(store.get(resourceType))
                .defaultIfEmpty(Collections.emptyList());
    }
}
