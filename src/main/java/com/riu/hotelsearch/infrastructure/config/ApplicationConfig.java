package com.riu.hotelsearch.infrastructure.config;

import com.riu.hotelsearch.application.CountService;
import com.riu.hotelsearch.application.PersistSearchService;
import com.riu.hotelsearch.application.SearchService;
import com.riu.hotelsearch.application.ports.CountUseCase;
import com.riu.hotelsearch.application.ports.PersistSearchUseCase;
import com.riu.hotelsearch.application.ports.SearchUseCase;
import com.riu.hotelsearch.domain.ports.out.SearchEventPublisher;
import com.riu.hotelsearch.domain.ports.out.SearchRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public CountUseCase countUseCase(SearchRepository repository) {
        return new CountService(repository);
    }

    @Bean
    public SearchUseCase searchUseCase(SearchEventPublisher publisher) {
        return new SearchService(publisher);
    }

    @Bean
    public PersistSearchUseCase persistSearchUseCase(SearchRepository repository) {
        return new PersistSearchService(repository);
    }
}
