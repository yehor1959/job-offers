package com.jobOffers.jobOffers.infrastructure.offer.http;

import com.jobOffers.jobOffers.domain.offer.OfferFetcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class OfferFetcherClientConfig {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(@Value("${job-offers.offers.http.client.config.connectionTimeout:1000}") long connectionTimeout,
                                     @Value("${job-offers.offers.http.client.config.readTimeout:1000}") long readTimeout,
                                     RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {

        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    public OfferFetcher remoteOfferFetcherClient(RestTemplate restTemplate,
                                                 @Value("${job-offers.offers.http.client.config.uri}") String uri,
                                                 @Value("${job-offers.offers.http.client.config.port}") int port) {
        return new OfferFetcherRestTemplate(restTemplate, uri, port);
    }
}
