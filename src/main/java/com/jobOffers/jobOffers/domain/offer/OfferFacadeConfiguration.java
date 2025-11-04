package com.jobOffers.jobOffers.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OfferFacadeConfiguration {

    @Bean
    OfferFacade offerFacade(OfferFetcher offerFetcher, OfferRepository offerRepository) {
        OfferService offerService = new OfferService(offerFetcher, offerRepository);
        return new OfferFacade(offerRepository, offerService);
    }
}
