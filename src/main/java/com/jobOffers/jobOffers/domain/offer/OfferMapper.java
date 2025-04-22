package com.jobOffers.jobOffers.domain.offer;

import com.jobOffers.jobOffers.domain.offer.dto.ExternalOfferDto;

public class OfferMapper {
    public Offer map(ExternalOfferDto dto) {
        return new Offer(dto.getId(), dto.getTitle(), dto.getCompany(), dto.getUrl());
    }
}
