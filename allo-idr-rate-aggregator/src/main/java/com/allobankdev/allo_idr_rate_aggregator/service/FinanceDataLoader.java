package com.allobankdev.allo_idr_rate_aggregator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.allobankdev.allo_idr_rate_aggregator.strategy.IDRDataFetcher;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class FinanceDataLoader implements ApplicationRunner {

    private final List<IDRDataFetcher> fetchers;
    private final FinanceDataStore store;

    @Override
    public void run(ApplicationArguments args) {

        Map<String, List<Object>> data =
                fetchers.stream()
                        .map(f -> f.fetch()
                                .map(r -> Map.entry(f.resourceType(), r))
                                .block())
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        ));

        store.initialize(data);
    }
}

