# MUSIC CONTESTS PORTAL
Music Contests Portal - Portal Konkursów Muzycznych. 

JAK URUCHOMIĆ? 
Do uruchomienia aplikacji musisz posiadać zainstalowanego Dockera. Z głównego katalogu 'music-portal' uruchom
komendę z terminala "docker compose up" - uruchomi się wtedy kontener dockera złożony z obrazów dwóch aplikacji 
(music-contests oraz instrument-storage) oraz obrazy baz danych dla każdej z nich (postgres).
Strona startowa aplikacji po uruchomieniu kontenera: http://localhost:9090/music-contests/authentication/login

Obie aplikacje, z których składa się program Portalu Konkursów Muzycznych wystawiają warstwę RESTful API, dokumentacja:
http://localhost:9090/music-contests/swagger-ui/index.html
http://localhost:9092/instrument-storage/swagger-ui/index.html

Więcej informacji o kwestiach technicznych na końcu pliku.

O APLIKACJI
Aplikacja, która w jednym miejscu skupia wszystkie konkursy muzyczne ze wszystkich szkół muzycznych I i II stopnia 
z całego kraju, pozwala je tworzyć, wyszukiwać, zgłaszać oraz wycofywać z nich uczniów, ogłaszać i przeglądać wyniki.
Konkursy mogą być tworzone podziałem na wiele kategorii takich jak:
- online (czy konkurs jest stacjonarny, czy online)
- stopień konkursu (czy konkurs jest dla pierwszego, czy drugiego stopnia szkoły muzycznej - lub dla obu naraz)
- instrument
- miasto konkursu
- inne

Aplikacja daje użytkownikom możliwość sprawdzenia jakie konkursy odbędą się w najbliższej przyszłości 
oraz zapisania się do wzięcia w nich udziału. Do wyboru użytkownik ma trzy wyszukiwarki konkursów:
- wyszukiwarka ogólna - wyświetla wszystkie aktualnie dostępne konkursy
- wyszukiwarka po instrumencie
- wyszukiwarka po kilku filtrach naraz

Aplikacja pełni również funkcje informacyjne, wyszukane konkursy zawierają takie informacje o konkursach jak:
- data rozpoczęcia i zakończenia konkursu 
- ostateczna data, do której można zgłosić ucznia do konkursu
- termin, w którym organizator konkursu zobowiązuje się ogłosić wyniki na portalu konkursów muzycznych
- lokalizacja konkursu
- organizator konkursu
- wymagania konkursu - cały opis wymaganych do zagrania utworów dla danych kategorii wiekowych itd. 

Aplikacja daje również użytkownikom możliwość sprawdzenia wyników konkursów, które już się zakończyły.

Aplikacja składa się z trzech głównych modułów oraz czwartego - administratora, 
który jako jedyny ma dostęp do wszystkich innych.
Wszystkie moduły różnią się funkcjonalnością oraz poziomem uprawnień użytkownika:
- Moduł administratora
- Moduł dyrektora
- Moduł nauczyciela
- Moduł ucznia

Moduł dyrektora - dyrektor ma następujące uprawnienia, może:
- tworzyć konkursy muzyczne ze wszystkimi wytycznymi oraz udostępniać je na portalu dla innych
  (konkurs może odbywać się w jego szkole muzycznej lub dowolnej innej lokalizacji)
- jeśli oprócz funkcji dyrektora również jest czynnym nauczycielem, może założyć również konto nauczyciela
  (i korzystać z jego wszystkich uprawnień i funkcjonalności)
- wyszukiwać konkursy korzystając z kilku różnych wyszukiwarek - wyniki są sortowane i stronicowane po 5 na stronę
- do wyszukanych konkursów zgłosić swojego ucznia/swoich uczniów - tylko do określonego przy tworzeniu konkursu terminu
- wycofać ucznia z konkursu maksymalnie 2 godziny przed rozpoczęciem konkursu
- utworzyć wyniki dla stworzonego przez siebie konkursu
- sprawdzić wyniki dowolnego wybranego przez siebie zakończonego konkursu
- wyszukać wszystkich nauczycieli pracujących w jego szkole
- wyszukać wszystkich swoich uczniów
- wyszukać wszystkich uczniów ze swojej szkoły
- wyszukać wszystkich uczniów biorących udział w wybranym konkursie
- wyszukać swoje lub innego nauczyciela formularze zgłoszeniowe uczniów do danego konkursu
- wyszukać stworzone przez siebie konkursy
- sprawdzić wyniki swoich uczniów
- sprawdzić wyniki wszystkich uczniów

Moduł nauczyciela - nauczyciel ma następujące uprawnienia, może:
- wyszukiwać konkursy korzystając z kilku różnych wyszukiwarek - wyniki są sortowane i stronicowane po 5 na stronę
- do wyszukanych konkursów zgłosić swojego ucznia/swoich uczniów - tylko do określonego przy tworzeniu konkursu terminu
- wycofać ucznia z konkursu maksymalnie 2 godziny przed rozpoczęciem konkursu
- sprawdzić wyniki swoich uczniów dowolnego wybranego przez siebie zakończonego konkursu
- wyszukać wszystkich swoich uczniów
- wyszukać konkretnego ucznia biorącego udział w wybranym konkursie
- wyszukać swoje formularze zgłoszeniowe uczniów do danego konkursu
- sprawdzić wyniki swoich uczniów

Moduł ucznia - uczeń ma następujące uprawnienia, może:
- wyszukiwać konkursy korzystając z kilku różnych wyszukiwarek - wyniki są sortowane i stronicowane po 5 na stronę
- wyszukać konkursy, w których brał udział
- sprawdzić wyniki zakończonego konkursu, w którym brał udział - które zajął miejsce i czy np. otrzymał nagrodę specjalną

Każdy z użytkowników może natomiast:
- stworzyć konto w portalu Konkursy Muzyczne (różne formularze dla różnych typów kont)
- przypisać swoje konto do danej szkoły muzycznej (dyrektor szkoły może być tylko jeden, nauczycieli i uczniów wielu)
- stworzyć szkołę muzyczną, jeśli jeszcze nie istnieje w bazie
- znaleźć szkołę muzyczną, korzystając z kilku wyszukiwarek (np. przez patrona szkoły)
- usunąć swoje konto
- wyszukać wszystkie instrumenty z podziałem na kategorie
- wyszukać wszystkie miasta, w których aktualnie odbywają się konkursy
- zalogować się na swoje konto - każdy moduł ma osobny swój mini-portal tylko ze swoimi funkcjonalnościami
  (loginem jest email, a hasłem pesel użytkownika)
- hasła użytkowników są szyfrowane (Bcrypt) i zakodowane trzymane w bazie danych 

Do aplikacji są dodane przykładowe dane jako skrypty migracyjne flyway.
Można korzystać ze wstępnie utworzonych kont, czy konkursów. 
Najlepsza zabawa jest jednak wtedy, gdy stworzy się samemu wszystko od początku!
Można też skorzystać z panelu administratora i przejrzeć dostęp do wszystkich funkcjonalności aplikacji.

# Panel administratora
login: admin@music-contests.pl
password: 00000000000

Warstwa wizualna jest moją wariacją na temat, jak ta aplikacja mogłaby przykładowo wyglądać w praktyce.
Pełna funkcjonalność dostępna jest w warstwie restowej.
Wizualna aplikacja dostępna w przeglądarce jest zabezpieczona podstawowym security i każdy użytkownik ma dostęp
tylko i wyłącznie do danych funkcjonalności w zależności od roli, jaką posiada - dyrektor, nauczyciel, uczeń (lub admin).
Wystawione Rest API jest natomiast ogólnie dostępne, aby zobaczyć całą funkcjonalność i podział modułów aplikacji.

# Kwestie techniczne:
- Warstwa wizualna/html: thymeleaf + bootstrap
- Java 17
- Oprócz warstwy kontrolerów html aplikacja wystawia też RESTful api
- Docker: aplikacja jest oparta o docker compose (dwie aplikacje z bazami danych w jednym kontenerze).

Każda z dwóch aplikacji składających się na program Portalu Konkursów Muzycznych może być uruchomiana osobno w IntelliJ lub innym IDE.
Music Contests port: 8080, baza danych: 5432
Instrument Storage port: 8082 baza danych: 5432
LINKI STARTOWE
*MUSIC CONTESTS
- lokalnie:
  http://localhost:8080/music-contests/authentication/login

  http://localhost:8080/music-contests/swagger-ui/index.html

  http://localhost:8080/music-contests/admin - portal administratora (po zalogowaniu)

  DB PostreSQL: 5432

- docker:
  http://localhost:9090/music-contests/authentication/login

  http://localhost:9090/music-contests/swagger-ui/index.html

  http://localhost:9090/music-contests/admin - portal administratora (po zalogowaniu)

DB port: 8000

*INSTRUMENT STORAGE
- lokalnie:
  http://localhost:8082/instrument-storage/swagger-ui/index.html

DB PostreSQL: 5432

- docker:
  http://localhost:9092/instrument-storage/swagger-ui/index.html

DB port: 8002

# Uwagi:
Sama aplikacja jest pisana w języku angielskim, kod oraz wszystkie komunikaty błędów, nazwy encji, pól itd., 
natomiast sam interfejs "frontowy" jest w języku polskim, który ma symulować dedykowaną wersje dla
polskiego szkolnictwa, bo z myślą o nim była tworzona ta apka:))

kontakt do mnie: mareczek100@wp.pl