package com.jobOffers.jobOffers.domain.offer;

import com.jobOffers.jobOffers.domain.offer.dto.ExternalOfferDto;

import java.util.List;

public interface OfferFetcher {
    List<ExternalOfferDto> fetchOffers();
}
