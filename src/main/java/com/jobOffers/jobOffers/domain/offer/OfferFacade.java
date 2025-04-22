package com.jobOffers.jobOffers.domain.offer;

import com.jobOffers.jobOffers.domain.offer.dto.ExternalOfferDto;
import org.springframework.dao.DuplicateKeyException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OfferFacade {

    private final OfferRepository offerRepository;
    private final OfferFetcher offerFetcher;
    private final OfferMapper offerMapper;

    public void fetchAllOffersAndSaveAll() {
        List<ExternalOfferDto> externalOffers = offerFetcher.fetchOffers();
        List<Offer> existingOffers = offerRepository.findAll();

        Set<String> existingTitles = existingOffers.stream()
                .map(Offer::getTitle)
                .collect(Collectors.toSet());

        Set<String> existingUrls = existingOffers.stream()
                .map(Offer::getUrl)
                .collect(Collectors.toSet());

        for (ExternalOfferDto externalOffer : externalOffers) {
            if (existingUrls.contains(externalOffer.getUrl())) {
                throw new DuplicateKeyException("Offer with URL " + externalOffer.getUrl() + " already exists");
            }
        }

        List<Offer> newOffers = externalOffers.stream()
                .filter(externalOffer -> existingTitles.contains(externalOffer.getTitle()))
                .map(offerMapper::map)
                .toList();

        offerRepository.saveAll(newOffers);
    }

    public Offer getOfferById(Long id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Offer with id " + id + " not found"));
    }
}
