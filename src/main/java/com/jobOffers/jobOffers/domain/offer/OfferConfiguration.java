package com.jobOffers.jobOffers.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class OfferConfiguration {

    @Bean
    OfferRepository offerRepository() {
        return new OfferRepository() {
            @Override
            public boolean existsByOfferUrl(String offerUrl) {
                return false;
            }

            @Override
            public Optional<Offer> findByOfferUrl(String offerUrl) {
                return Optional.empty();
            }

            @Override
            public List<Offer> findAll() {
                return List.of();
            }

            @Override
            public List<Offer> saveAll(List<Offer> offers) {
                return List.of();
            }

            @Override
            public Optional<Offer> findById(String id) {
                return Optional.empty();
            }

            @Override
            public Offer save(Offer offer) {
                return null;
            }
        };
    }

    @Bean
    OfferService offerService(OfferFetcher fetcher, OfferRepository offerRepository) {
        return new OfferService(fetcher, offerRepository);
    }

    @Bean
    OfferFacade offerFacade(OfferRepository offerRepository, OfferService offerService) {
        return new OfferFacade(offerRepository, offerService);
    }
}
