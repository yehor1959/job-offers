package com.jobOffers.feature;

import com.jobOffers.BaseIntegrationTest;
import org.junit.jupiter.api.Test;

public class UserSearchAndAddJobOffer extends BaseIntegrationTest {

    @Test
    public void should_user_search_or_add_job_offer() {

        //    step 1: external HTTP service returns job offers from multiple websites
        //    step 2: client registered via POST /register with email and password, received 201 CREATED
        //    step 3: client logged in via POST /login and received a valid token
        //    step 4: client made GET /offers with the token and system returned 200 OK with all available offers
        //    step 5: system fetched offers from external service and saved them to the database at 10:00
        //    step 6: system fetched offers again at 13:00, duplicates were ignored based on URL
        //    step 7: client made another GET /offers at 13:15 — system returned offers from cache (same as previous request), no DB query made
        //    step 8: client made GET /offers/{offerId} with a valid ID and system returned 200 OK with single offer
        //    step 9: client made POST /offers with a new job offer manually, system saved it and returned 201 CREATED
        //    step 10: each offer returned contained: URL, position title, company name, and salary range (if provided)
    }
}
