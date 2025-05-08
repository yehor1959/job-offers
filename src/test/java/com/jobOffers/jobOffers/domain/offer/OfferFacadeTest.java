package com.jobOffers.jobOffers.domain.offer;

import com.jobOffers.jobOffers.domain.offer.dto.JobOfferResponse;
import com.jobOffers.jobOffers.domain.offer.dto.OfferRequestDto;
import com.jobOffers.jobOffers.domain.offer.dto.OfferResponseDto;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class OfferFacadeTest {

    @Test
    public void should_save_four_offers_when_there_are_no_offers_in_database() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();

        // when
        offerFacade.saveOffer(new OfferRequestDto("id", "asds", "asdasd", "1"));
        offerFacade.saveOffer(new OfferRequestDto("id", "asds", "asdasd", "2"));
        offerFacade.saveOffer(new OfferRequestDto("id", "asds", "asdasd", "3"));
        offerFacade.saveOffer(new OfferRequestDto("id", "asds", "asdasd", "4"));

        // then
        assertThat(offerFacade.findAllOffers()).hasSize(4);
    }

    @Test
    public void should_save_only_two_offers_when_repository_had_four_added_with_offer_urls() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(
                List.of(
                        new JobOfferResponse("id", "id", "asds", "1"),
                        new JobOfferResponse("assd", "id", "asds", "2"),
                        new JobOfferResponse("asddd", "id", "asds", "3"),
                        new JobOfferResponse("asfd", "id", "asds", "4"),
                        new JobOfferResponse("Junior", "Comarch", "1000", "https://someurl.pl/5"),
                        new JobOfferResponse("Mid", "Finanteq", "2000", "https://someother.pl/6")
                )
        ).offerFacadeForTests();

        offerFacade.saveOffer(new OfferRequestDto("id", "asds", "asdasd", "1"));
        offerFacade.saveOffer(new OfferRequestDto("id", "asds", "asdasd", "2"));
        offerFacade.saveOffer(new OfferRequestDto("id", "asds", "asdasd", "3"));
        offerFacade.saveOffer(new OfferRequestDto("id", "asds", "asdasd", "4"));
        assertThat(offerFacade.findAllOffers()).hasSize(4);

        // when
        List<OfferResponseDto> response = offerFacade.fetchAllOffersAndSaveAllIfNotExisting();

        // then
        assertThat(List.of(
                        response.get(0).offerUrl(),
                        response.get(1).offerUrl()
                )
        ).containsExactlyInAnyOrder("https://someurl.pl/5", "https://someother.pl/6");
    }

    @Test
    public void should_throw_duplicate_key_exception_when_with_offer_url_exists() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(List.of()).offerFacadeForTests();
        OfferResponseDto offerResponseDto = offerFacade.saveOffer(new OfferRequestDto("id", "asds", "asdasd", "hello.pl"));
        String savedId = offerResponseDto.id();
        assertThat(offerFacade.findOfferById(savedId).id()).isEqualTo(savedId);

        // when
        Throwable thrown = catchThrowable(() -> offerFacade.saveOffer(
                new OfferRequestDto("cx", "vc", "xcv", "hello.pl")));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(OfferDuplicateException.class)
                .hasMessage("Offer with offerUrl [hello.pl] already exists");
    }

    @Test
    public void should_throw_not_found_exception_when_user_requests_non_existing_offer_by_id() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        assertThat(offerFacade.findAllOffers()).isEmpty();

        // when
        Throwable thrown = catchThrowable(() -> offerFacade.findOfferById("100"));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(OfferNotFoundException.class)
                .hasMessage("Offer with id 100 not found");
    }

    @Test
    public void should_fetch_from_jobs_from_remote_and_save_all_offers_when_repository_is_empty() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        assertThat(offerFacade.findAllOffers()).isEmpty();

        // when
        List<OfferResponseDto> result = offerFacade.fetchAllOffersAndSaveAllIfNotExisting();

        // then
        assertThat(result).hasSize(6);
    }

    @Test
    public void should_find_offer_by_id_when_offer_was_saved() {
        // given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        OfferResponseDto offerResponseDto = offerFacade.saveOffer(new OfferRequestDto("id", "asds", "asdasd", "1"));

        // when
        OfferResponseDto offerById = offerFacade.findOfferById(offerResponseDto.id());

        // then
        assertThat(offerById).isEqualTo(OfferResponseDto.builder()
                .id(offerResponseDto.id())
                .companyName("id")
                .position("asds")
                .salary("asdasd")
                .offerUrl("1")
                .build()
        );
    }

}