package com.jobOffers.jobOffers.domain.offer;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document
record Offer(
        String id,
        String companyName,
        String position,
        String salary,
        String offerUrl
) {
}
