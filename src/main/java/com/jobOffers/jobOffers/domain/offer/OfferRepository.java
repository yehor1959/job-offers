package com.jobOffers.jobOffers.domain.offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {

    List<Offer> findAll();

    void saveAll(List<Offer> offers);

    Optional<Offer> findById(Long id);

}
