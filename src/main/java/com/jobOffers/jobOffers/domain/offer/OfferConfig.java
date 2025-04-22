package com.jobOffers.jobOffers.domain.offer;

import org.springframework.context.annotation.Bean;

public class OfferConfig {

    @Bean
    public OfferFacade offerFacade(OfferRepository offerRepository, OfferFetcher offerFetcher) {
        return new OfferFacade(offerRepository, offerFetcher, new OfferMapper());
    }
}
