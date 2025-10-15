package com.jobOffers.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.jobOffers.BaseIntegrationTest;
import com.jobOffers.SampleJobOfferResponse;
import com.jobOffers.jobOffers.domain.offer.OfferFacade;
import com.jobOffers.jobOffers.domain.offer.OfferFetcher;
import com.jobOffers.jobOffers.domain.offer.OfferSavingException;
import com.jobOffers.jobOffers.domain.offer.dto.JobOfferResponse;
import com.jobOffers.jobOffers.domain.offer.dto.OfferResponseDto;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TypicalScenarioUserWantToSeeOffersIntegrationTest extends BaseIntegrationTest implements SampleJobOfferResponse {

    @Autowired
    OfferFetcher offerHttpClient;
    @Autowired
    OfferFacade offerFacade;

    @Test
    public void user_want_to_see_offers_but_have_to_be_logged_in_and_external_server_should_have_some_offers() {

        //  step 1: there are no offers in external HTTP server (http://ec2-3-120-147-150.eu-central-1.compute.amazonaws.com:5057/offers)
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", "application/json")
                .withBody(bodyWithFourOffersJson())));

        List<JobOfferResponse> jobOfferResponses = offerHttpClient.fetchOffers();

        //  step 2: system fetched job offers for some date
        //  given
        LocalDateTime fetchDate = LocalDateTime.of(2023, 2, 25, 12, 0, 0);

        // when & then
        Awaitility.await()
                .atMost(Duration.ofSeconds(20))
                .pollInterval(Duration.ofSeconds(1))
                .until(() -> {
                    try {
                        List<OfferResponseDto> offers =
                                offerFacade.fetchAllOffersAndSaveAllIfNotExisting(fetchDate, null);
                        // Return true if some offers were actually fetched/saved
                        return offers != null && !offers.isEmpty();
                    } catch (OfferSavingException e) {
                        // The system may still be initializing — retry
                        return false;
                    }
                });

        // finally, verify that offers are now present in DB
        List<OfferResponseDto> allOffers = offerFacade.fetchAllOffersAndSaveAllIfNotExisting(fetchDate, null);
        assertThat(allOffers)
                .isNotNull()
                .isNotEmpty()
                .allSatisfy(offer -> {
                    assertThat(offer.position()).isNotBlank();
                    assertThat(offer.id()).isNotNull();
                });

        //  when

//        await()
//                .atMost(Duration.ofSeconds(20))
//                .pollInterval(Duration.ofSeconds(1))
//                .until(() -> {
//                    try {
//                        return offerFacade.fetchAllOffersAndSaveAllIfNotExisting(fetchDate, null)
//                    } catch (OfferSavingException e) {
//                        return false;
//                    }
//                });

        //  step 1, obok HTTP pojavie sie scheduler i ten scheduler pobiera z metodki w OfferFacade fetchAllOffersAndSaveAllIfNotExists
        //  step 2, uruchmić Scheduler w testie integraciynym i dodać properties
        //  step
        //  step 2: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        //  step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        //  step 4: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401)
        //  step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status OK(200)
        //  step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
        //  step 7: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 0 offers
        //  step 8: there are 2 new offers in external HTTP server
        //  step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1000 and 2000 to database
        //  step 10: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids: 1000 and 2000
        //  step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message “Offer with id 9999 not found”
        //  step 12: user made GET /offers/1000 and system returned OK(200) with offer
        //  step 13: there are 2 new offers in external HTTP server
        //  step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database
        //  step 15: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1000,2000, 3000 and 4000
    }
}
