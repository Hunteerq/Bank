# KMB

Celem projektu KMB (Kulig Merta Bank) jest stworzenie w pełni funkcjonalnego i bezpiecznego banku wraz z funkcjonującym kantorem walutowym, oraz kryptowalutowym.

Do funkcji systemu zaliczać się będzie: prowadzenie dowolnej ilości rachunków, oraz kart

wraz z wykonywanie przelewów, lub przelewów cyklicznych na konto o dowolnej walucie/kryptowalucie. System będzie odpowiadał za poprawne przewalutowanie kwoty transakcji.Użytkownik będzie mógł wyświetlać historię transakcji, ustawić limity, oraz blokady do każdego konta i karty. Z dodatkowych funkcji będzie branie kredytów, oraz lokat bankowych.

System będzie składał się z czterech modułów

1. server - odpowiedzialny za zabezpieczenia, generowanie stron, oraz podstawowe funkcjonalności banku, wszelkie transakcje będą wrzucane na kolejkę RabbitMQ.

1. transaction\_logger -  zadaniem tego modułu będzie pobieranie transakcji z kolejki, oraz realizacja ich, tj. logowanie zmian w bazie danych, a w przypadku niepowodzenia wycofanie ich.

2. loans\_investments - będzie odpowiedzialny za przeglądanie bazy kredytów, oraz lokat, automatyczne pobieranie kwoty pożyczki z kont pożyczkobiorcow, oraz sprawdzanie wszelkich lokat i w przypadku zakończenia przesyłanie pieniędzy na konto klienta.

3. recurring\_transfer - jest to moduł odpowiedzialny za przelewy cykliczne. W przypadku gdy moduł zauważy pełen cykl w bazie danych, będzie wrzucał transakcję na kolejkę, gdzie transaction\_logger wykona ją.

Wykorzystywane technologie przy procesie tworzenia:

1. Java - język programowania strony serwerowej.
2. Spring - framework użyty do postawienia serwera, komunikację i zabezpieczenia.
3. RabbitMQ - kolejka komunikatów do transakcji.
4. MongoDB- baza danych przechowująca historię transakcji.
5. Postgresql - baza danych przechowująca pozostałe informacje banku.
6. Thymeleaf - silnik szablonów do generowania htmla po stronie serwera.
7. Swagger - framework używany do generowania dokumentacji systemu.
8. HTML/CSS - języki znacznikowe używane do generowania stron internetowych, oraz styli.
9. JavaScript - język programowania po stronie frontu.

