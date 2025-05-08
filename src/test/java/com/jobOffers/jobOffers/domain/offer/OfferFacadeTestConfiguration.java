package com.jobOffers.jobOffers.domain.offer;

import com.jobOffers.jobOffers.domain.offer.dto.JobOfferResponse;

import java.util.List;

public class OfferFacadeTestConfiguration {

    private final InMemoryFetcherTestImpl fetcher;
    private final InMemoryOfferRepository repository;

    OfferFacadeTestConfiguration() {
        this.fetcher = new InMemoryFetcherTestImpl(
                List.of(
                        new JobOfferResponse("id", "id", "asds", "1"),
                        new JobOfferResponse("assd", "id", "asds", "2"),
                        new JobOfferResponse("asddd", "id", "asds", "3"),
                        new JobOfferResponse("asfd", "id", "asds", "4"),
                        new JobOfferResponse("agsd", "id", "asds", "5"),
                        new JobOfferResponse("adfvsd", "id", "asds", "6")
                )
        );
        this.repository = new InMemoryOfferRepository();
    }

    OfferFacadeTestConfiguration(List<JobOfferResponse> remoteClientOffers) {
        this.fetcher = new InMemoryFetcherTestImpl(remoteClientOffers);
        this.repository = new InMemoryOfferRepository();
    }

    OfferFacade offerFacadeForTests() {
        return new OfferFacade(repository, new OfferService(fetcher, repository));
    }
}
