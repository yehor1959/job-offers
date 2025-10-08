package com.jobOffers.jobOffers.infrastructure.offer.scheduler;

import com.jobOffers.jobOffers.domain.offer.OfferFacade;
import com.jobOffers.jobOffers.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
public class OfferScheduler {

    private final OfferFacade offerFacade;

    @Scheduled(cron = "*/5 * * * * *")
    public List<OfferResponseDto> fetchOffers() {
        log.info("Offer scheduler started");
        List<OfferResponseDto> offerResponseDto = offerFacade.fetchAllOffersAndSaveAllIfNotExisting();
        log.info(offerResponseDto.toString());
        return offerResponseDto;
    }
}
