Happy path scenario:

As a user I want to go to a web page and find job offers for Java developers. 

1. There are no offers in external HTTP server;
2. Scheduler run 1st time and made GET to external server and system added 0 offers to the database;
3. User try to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401);
4. User made GET /offers with no JWT token and system returned UNAUTHORIZED(401);
5. User made POST /register with username=someUser, password=somePassword and system registered user with status OK(200);
6. User tried to get JWT token by requesting POST /token with userName=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC;
7. User made GET /offers with header "Authorization: Bearer AAAA.BBBB.CCC" and system returned OK(200) with 0 offers;
8. There are two new offers in external HTTP server;
9. Scheduler run 2nd time and made GET to external server and system added two new offers with Ids: 1000 and 2000 to the database;
10. User made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids: 1000 and 2000;
11. User made GET /offers/9999 and system returned NOT_FOUND(404) with message “Offer with id 9999 not found”;
12. User made GET /offers/1000 and system returned OK(200) with offer;
13. There are 2 new offers in external HTTP server;
14. Scheduler run 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database;
15. User made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1000,2000, 3000 and 4000;



Modules:

1. offers CRUD and periodically fetching offers from external service = Offer;
2. finding and registering users = LoginAndRegister;
3. auth and generating JWT = JwtAuthentication;