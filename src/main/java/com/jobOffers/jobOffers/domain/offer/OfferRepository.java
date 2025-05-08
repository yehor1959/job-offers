package com.jobOffers.jobOffers.domain.offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {

    boolean existsByOfferUrl(String offerUrl);

    Optional<Offer> findByOfferUrl(String offerUrl);

    List<Offer> findAll();

    List<Offer> saveAll(List<Offer> offers);

    Optional<Offer> findById(String id);

    Offer save(Offer offer);

}
