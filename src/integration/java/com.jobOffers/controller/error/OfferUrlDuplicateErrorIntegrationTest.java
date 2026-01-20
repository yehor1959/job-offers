package com.jobOffers.controller.error;

import com.jobOffers.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OfferUrlDuplicateErrorIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_409_conflict_when_added_second_offer_with_same_offer_url() throws Exception {
        // step 1
        // given
        String requestJson = """
                {
                        "companyName": "someCompany",
                        "position": "somePosition",
                        "salary": "7 000 - 9 000 PLN",
                        "offerUrl": "https://newoffers.pl/offer/1234"
                }
                """;
        // when
        ResultActions perform = mockMvc.perform(post("/offers")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
        );
        // then
        perform.andExpect(status().isCreated());

        // step 2
        // given
        String sameRequestJson = """
                {
                        "companyName": "someCompany",
                        "position": "somePosition",
                        "salary": "7 000 - 9 000 PLN",
                        "offerUrl": "https://newoffers.pl/offer/1234"
                }
                """;
        // when
        ResultActions sameRequestPerform = mockMvc.perform(post("/offers")
                .content(sameRequestJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
        );
        // then
        sameRequestPerform.andExpect(status().isConflict());
    }
}
