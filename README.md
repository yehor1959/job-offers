WYMAGANIA

# Jako klient aplikacji chcę widzieć oferty pracy dla Junior Java Developera

1. Korzystamy ze zdalnego serwera HTTP (skrypt który pobiera oferty ze stron WWW)
2. Klient musi używać tokena, żeby zobaczyć oferty
3. Klient może się zarejestrować
4. Aktualizacja ofert w bazie danych jest co 3 godziny (wtedy odpytujemy zdalny serwer z pkt. 1)
5. Oferty w bazie nie mogą się powtarzać (decyduje url oferty)
6. Klient może pobrać jedną ofertę pracy poprzez unikalne Id
7. Klient może pobrać wszystkie dostępne oferty kiedy jest zautoryzowany
8. Jeśli klient w ciągu 60 minut robi więcej niż jedno zapytanie, to dane powinny pobierać się z cache (ponieważ pobieranie z bazy danych kosztuję pieniądze naszego klienta)
9. Klient może ręcznie dodać ofertę pracy
10. Każda oferta pracy ma (link do oferty, nazwę stanowiska, nazwę firmy, zarobki (mogą być widełki))

