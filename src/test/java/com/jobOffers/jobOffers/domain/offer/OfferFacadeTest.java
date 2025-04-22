package com.jobOffers.jobOffers.domain.offer;

import com.jobOffers.jobOffers.domain.offer.dto.ExternalOfferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.dao.DuplicateKeyException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OfferFacadeTest {

    private OfferRepository offerRepository;
    private OfferFetcher offerFetcher;
    private OfferMapper offerMapper;
    private OfferFacade offerFacade;

    @BeforeEach
    void setUp() {
        offerRepository = mock(OfferRepository.class);
        offerFetcher = mock(OfferFetcher.class);
        offerMapper = new OfferMapper();
        offerFacade = new OfferFacade(offerRepository, offerFetcher, offerMapper);
    }

    @Test
    public void should_save_four_offers_when_there_are_no_offers_in_database() {
        // given
        when(offerRepository.findAll()).thenReturn(Collections.emptyList());

        List<ExternalOfferDto> externalOffers = List.of(
                new ExternalOfferDto(1L, "Java Dev", "Company A", "https://a.com"),
                new ExternalOfferDto(2L,"Senior Java Dev", "Company B", "https://b.com"),
                new ExternalOfferDto(3L,"Spring Boot Dev", "Company C", "https://c.com"),
                new ExternalOfferDto(4L,"Backend Dev", "Company D", "https://d.com")
        );
        when(offerFetcher.fetchOffers()).thenReturn(externalOffers);

        // when
        offerFacade.fetchAllOffersAndSaveAll();

        // then
        ArgumentCaptor<List<Offer>> captor = ArgumentCaptor.forClass(List.class);
        verify(offerRepository).saveAll(captor.capture());
        assertEquals(4, captor.getValue().size());
    }

    @Test
    public void should_save_only_two_offers_when_repository_had_four_added_with_offer_urls() {
        // given
        List<Offer> existingOffers = List.of(
                new Offer(1L,"Java Developer", "Company A", "https://a.com"),
                new Offer(2L,"Backend Developer", "Company B", "https://b.com"),
                new Offer(3L,"Spring Boot Dev", "Company C", "https://c.com"),
                new Offer(4L,"Software Engineer", "Company D", "https://d.com")
        );

        when(offerRepository.findAll()).thenReturn(existingOffers);

        List<ExternalOfferDto> fetchedOffers = List.of(
                new ExternalOfferDto(1L,"Java Developer", "Company A", "https://a.com"),
                new ExternalOfferDto(2L,"Backend Developer", "Company B", "https://b.com"),
                new ExternalOfferDto(3L,"Cloud Engineer", "Company E", "https://e.com"),
                new ExternalOfferDto(4L,"DevOps Engineer", "Company F", "https://f.com")
        );

        when(offerFetcher.fetchOffers()).thenReturn(fetchedOffers);

        // when
        offerFacade.fetchAllOffersAndSaveAll();

        // then
        ArgumentCaptor<List<Offer>> captor = ArgumentCaptor.forClass(List.class);
        verify(offerRepository).saveAll(captor.capture());
        List<Offer> savedOffers = captor.getValue();

        assertEquals(2, savedOffers.size());

        assertTrue(savedOffers.stream().anyMatch(o -> o.getUrl().equals("https://e.com")));
        assertTrue(savedOffers.stream().anyMatch(o -> o.getUrl().equals("https://f.com")));
    }

    @Test
    public void should_throw_duplicate_key_exception_when_with_offer_url_exists() {
        // given
        List<Offer> existingOffers = List.of(
                new Offer(1L, "Java Developer", "Company A", "https://a.com")
        );

        when(offerRepository.findAll()).thenReturn(existingOffers);

        List<ExternalOfferDto> externalOffers = List.of(
                new ExternalOfferDto(2L,"Java Developer", "Company A", "https://a.com")
        );

        when(offerFetcher.fetchOffers()).thenReturn(externalOffers);

        // when & then
        assertThrows(DuplicateKeyException.class, () -> offerFacade.fetchAllOffersAndSaveAll());
    }

    @Test
    public void should_throw_not_found_exception_when_user_requests_non_existing_offer_by_id() {
        // given
        Long nonExistentId = 9999L;
        when(offerRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // when & then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> offerFacade.getOfferById(nonExistentId));

        assertEquals("Offer with id 9999 not found", exception.getMessage());
    }

    @Test
    public void should_fetch_from_jobs_from_remote_and_save_all_offers_when_repository_is_empty() {
        // given
        when(offerRepository.findAll()).thenReturn(Collections.emptyList());

        List<ExternalOfferDto> externalOffers = List.of(
                new ExternalOfferDto(1L,"Java Dev", "Company A", "https://a.com"),
                new ExternalOfferDto(2L,"Spring Dev", "Company B", "https://b.com"),
                new ExternalOfferDto(3L,"Backend Dev", "Company C", "https://c.com")
        );

        when(offerFetcher.fetchOffers()).thenReturn(externalOffers);

        // when
        offerFacade.fetchAllOffersAndSaveAll();

        // then
        ArgumentCaptor<List<Offer>> captor = ArgumentCaptor.forClass(List.class);
        verify(offerRepository).saveAll(captor.capture());
        List<Offer> savedOffers = captor.getValue();

        assertEquals(3, savedOffers.size());
        assertTrue(savedOffers.stream().anyMatch(o -> o.getUrl().equals("https://a.com")));
        assertTrue(savedOffers.stream().anyMatch(o -> o.getUrl().equals("https://b.com")));
        assertTrue(savedOffers.stream().anyMatch(o -> o.getUrl().equals("https://c.com")));
    }

    @Test
    public void should_find_offer_by_id_when_offer_was_saved() {
        // given
        Long offerId = 1000L;

        Offer savedOffer = new Offer(offerId,"Senior Java Developer", "CoolTech", "https://cooltech.jobs/java");

        when(offerRepository.findById(offerId)).thenReturn(Optional.of(savedOffer));

        // when
        Offer foundedOffer = offerFacade.getOfferById(offerId);

        // then
        assertNotNull(foundedOffer);
        assertEquals(offerId, foundedOffer.getId());
        assertEquals("Senior Java Developer", foundedOffer.getTitle());
        assertEquals("CoolTech", foundedOffer.getCompany());
        assertEquals("https://cooltech.jobs/java", foundedOffer.getUrl());
    }

}