package com.jobOffers.jobOffers.infrastructure.offer.controller;

import com.jobOffers.jobOffers.domain.offer.OfferFacade;
import com.jobOffers.jobOffers.domain.offer.dto.OfferRequestDto;
import com.jobOffers.jobOffers.domain.offer.dto.OfferResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public ResponseEntity<OfferResponseDto> saveOffer(@RequestBody OfferRequestDto offerRequestDto) {
        OfferResponseDto offerResponseDto = offerFacade.saveOffer(offerRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(offerResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferResponseDto> findOfferById(@PathVariable String id) {
        OfferResponseDto responseOfferByIdDto = offerFacade.findOfferById(id);
        return ResponseEntity.ok(responseOfferByIdDto);
    }
}
