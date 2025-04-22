package com.jobOffers.jobOffers.domain.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Offer {
    private final Long id;
    private final String title;
    private final String company;
    private final String url;
}
