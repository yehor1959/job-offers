package com.jobOffers.jobOffers.infrastructure.offer.controller;

import com.jobOffers.jobOffers.domain.offer.OfferFacade;
import com.jobOffers.jobOffers.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/offers")
@Log4j2
@AllArgsConstructor
public class OfferRestController {

    private final OfferFacade offerFacade;

    @GetMapping
    public ResponseEntity<List<OfferResponseDto>> findAllOffers() {
        List<OfferResponseDto> allOffers = offerFacade.findAllOffers();
        return ResponseEntity.ok(allOffers);
    }
}
