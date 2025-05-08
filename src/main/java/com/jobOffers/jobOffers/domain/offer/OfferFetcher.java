package com.jobOffers.jobOffers.domain.offer;

import com.jobOffers.jobOffers.domain.offer.dto.JobOfferResponse;

import java.util.List;

public interface OfferFetcher {
    List<JobOfferResponse> fetchOffers();
}
