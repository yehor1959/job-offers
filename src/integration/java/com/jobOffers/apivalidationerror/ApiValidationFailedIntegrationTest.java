package com.jobOffers.apivalidationerror;

import com.jobOffers.BaseIntegrationTest;
import com.jobOffers.jobOffers.infrastructure.apivalidation.ApiValidationErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_400_bad_request_and_validation_message_when_request_has_empty_input() throws Exception {
        // given
        String requestJson = """
                {
                    "companyName": "",
                    "position": "",
                    "salary": "",
                    "offerUrl": ""
                }
                """;

        // when
        ResultActions perform = mockMvc.perform(post("/offers")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto result = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(result.messages()).containsExactlyInAnyOrder(
                "inputNumbers must be not empty");
    }

    @Test
    public void should_return_400_bad_request_and_validation_message_when_request_does_not_have_input() throws Exception {
        // given
        String requestJson = """
                {}
                """;

        // when
        ResultActions perform = mockMvc.perform(post("/offers")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto result = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(result.messages()).containsExactlyInAnyOrder(
                "inputNumbers must not be null",
                "inputNumbers must be not empty");
    }
}
