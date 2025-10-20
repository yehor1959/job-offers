package com.jobOffers.scheduler;

import com.jobOffers.BaseIntegrationTest;
import com.jobOffers.jobOffers.JobOffersApplication;
import com.jobOffers.jobOffers.domain.offer.OfferFetcher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Duration;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest(classes = JobOffersApplication.class, properties = "scheduling.enabled=true")
public class HttpOffersSchedulerTest extends BaseIntegrationTest {

    @SpyBean
    OfferFetcher remoteOfferClient;

    @Test
    public void should_run_http_client_offers_fetching_exactly_given_times() {
        await().atMost(Duration.ofSeconds(2))
                .untilAsserted(() -> verify(remoteOfferClient, times(1)).fetchOffers());
    }
}
