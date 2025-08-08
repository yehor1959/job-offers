package com.jobOffers.jobOffers.infrastructure.offer.http;

import com.jobOffers.jobOffers.domain.offer.OfferFetcher;
import com.jobOffers.jobOffers.domain.offer.dto.JobOfferResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@Log4j2
public class OfferFetcherRestTemplate implements OfferFetcher {

    public static final String RANDOM_NUMBER_SERVICE_PATH = "/api/v1.0/random";

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public List<JobOfferResponse> fetchOffers() {
        log.info("Started fetching job offers using http client");
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        try {
            ResponseEntity<List<JobOfferResponse>> response = makeGetRequest(requestEntity);
            return response.getBody();
        } catch (ResourceAccessException e) {
            log.error("Error while fetching job offers using http client: " + e.getMessage());
        }
        return List.of();
    }

    private ResponseEntity<List<JobOfferResponse>> makeGetRequest(HttpEntity<HttpHeaders> requestEntity) {
        final String url = UriComponentsBuilder.fromHttpUrl(getUrlForService(RANDOM_NUMBER_SERVICE_PATH))
                .toUriString();
        ResponseEntity<List<JobOfferResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );

        return response;
    }

    private String getUrlForService(String service) {
        return uri + ":" + port;
    }
}
