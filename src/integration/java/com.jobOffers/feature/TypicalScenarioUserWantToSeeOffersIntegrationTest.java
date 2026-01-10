package com.jobOffers.feature;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.jobOffers.BaseIntegrationTest;
import com.jobOffers.SampleJobOfferResponse;
import com.jobOffers.jobOffers.domain.offer.OfferFetcher;
import com.jobOffers.jobOffers.domain.offer.dto.OfferResponseDto;
import com.jobOffers.jobOffers.infrastructure.offer.scheduler.HttpOfferScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

public class TypicalScenarioUserWantToSeeOffersIntegrationTest extends BaseIntegrationTest implements SampleJobOfferResponse {

    @Autowired
    OfferFetcher offerHttpClient;

    @Autowired
    HttpOfferScheduler offerScheduler;

    @Test
    public void user_want_to_see_offers_but_have_to_be_logged_in_and_external_server_should_have_some_offers() throws Exception {

        //  step 1: there are no offers in external HTTP server (http://ec2-3-120-147-150.eu-central-1.compute.amazonaws.com:5057/offers)
        // given && when && then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithFourOffersJson())));

        //  step 2: scheduler run 1st time and made GET to external server and system added 0 offers to database
        // given && when
        List<OfferResponseDto> newOffers = offerScheduler.fetchAllOffersAndSaveAllIfNotExists();
        // then
        assertThat(newOffers).isEmpty();

        //  step 1, OfferService
        //  step 1, TypicalIntegrationTest

        //  step 2: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        //  step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        //  step 4: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401)
        //  step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status OK(200)
        //  step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
        //  step 7: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 0 offers
        // given
        String offersUrl = "/offers";
        // when
        ResultActions perform = mockMvc.perform(get(offersUrl)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        MvcResult mvcResult = perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        List<OfferResponseDto> offers =
                objectMapper.readValue(json, new TypeReference<>() {
                });

        assertThat(offers).isNotNull();
        assertThat(offers).isNotEmpty();

        //  step 8: there are 2 new offers in external HTTP server
        //  step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1000 and 2000 to database
        //  step 10: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids: 1000 and 2000


        //  step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message “Offer with id 9999 not found”
        // given
        String nonExistingOfferUrl = "/offers/nonExistingOfferUrl";
        // when
        ResultActions performGetResultsWithNotExistingOfferUrl = mockMvc.perform(get(nonExistingOfferUrl));
        // then
        performGetResultsWithNotExistingOfferUrl.andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                                                "message": "Offer with id nonExistingOfferUrl not found",
                                                "status": "NOT_FOUND"
                                                }
                        """.trim()
                ));

        //  step 12: user made GET /offers/1000 and system returned OK(200) with offer
        //  step 13: there are 2 new offers in external HTTP server
        //  step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database
        //  step 15: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1000,2000, 3000 and 4000

        //step 16: user made POST /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and offer as body and system returned CREATED(201) with saved offer
        // given
        String requestJson = """
                {
                    "companyName": "Amazon",
                    "position": "Java Developer",
                    "salary": "15000 PLN",
                    "offerUrl": "https://amazon.com/careers/java-developer"
                }
                """;

        // when
        ResultActions performPostOffer = mockMvc.perform(post("/offers")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult mvcResultPostOffer = performPostOffer.andExpect(status().isCreated()).andReturn();
        String jsonPostOffer = mvcResultPostOffer.getResponse().getContentAsString();
        OfferResponseDto postOffer = objectMapper.readValue(jsonPostOffer, OfferResponseDto.class);
        assertThat(postOffer.companyName().equals("Amazon")).isTrue();

        //step 17: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 1 offer
        // given & when
        ResultActions performGetOffer = mockMvc.perform(get("/offers"));
        // then
        MvcResult mvcResultGetOffer = performGetOffer.andExpect(status().isOk()).andReturn();
//        String jsonGetOffer = mvcResultGetOffer.getResponse().getContentAsString();
//        OfferResponseDto getOffer = objectMapper.readValue(jsonGetOffer, OfferResponseDto.class);
//        assertThat(getOffer.companyName().equals("Amazon")).isTrue();
    }
}
