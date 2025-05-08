package com.jobOffers.jobOffers.domain.offer;

import com.jobOffers.jobOffers.domain.offer.dto.JobOfferResponse;
import com.jobOffers.jobOffers.domain.offer.dto.OfferRequestDto;
import com.jobOffers.jobOffers.domain.offer.dto.OfferResponseDto;

public class OfferMapper {

    public static OfferResponseDto mapFromOfferToOfferDto(Offer offer) {
        return OfferResponseDto.builder()
                .id(offer.id())
                .companyName(offer.companyName())
                .position(offer.position())
                .salary(offer.salary())
                .offerUrl(offer.offerUrl())
                .build();
    }

    public static Offer mapFromOfferDtoToOffer(OfferRequestDto offerDto) {
        return Offer.builder()
                .companyName(offerDto.companyName())
                .position(offerDto.position())
                .salary(offerDto.salary())
                .offerUrl(offerDto.offerUrl())
                .build();
    }

    public static Offer mapFromJobOfferResponseToOffer(JobOfferResponse jobOfferResponse) {
        return Offer.builder()
                .companyName(jobOfferResponse.company())
                .position(jobOfferResponse.title())
                .salary(jobOfferResponse.salary())
                .offerUrl(jobOfferResponse.offerUrl())
                .build();
    }
}
