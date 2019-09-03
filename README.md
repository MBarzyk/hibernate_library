Każdy model ma id.

Author:
- name,
- surname,
- date of birth,
- number of books (formula)
- books,

Book:
- title,
- year written,
- how old(formula),
- number of pages,
- number of available copies
- number of borrowed copies(formula)
- authors,
- current lents (set <BookLent>) (obecnie wypożyczenia)

BookLent:
- client,
- book,
- date lent,
- date returned,

Client:
- name,
- surname,
- id number, 
- set of lent books,
*** PublishingHouse: (wydawnictwo)
- name 
- set<Book>

Metody:
- dodawanie autorów,
- listowanie autorów
- usuwanie autorów
- modyfikacja imienia/nazwiska lub roku urodzeznia autora
- dodawanie książek,
- listowanie ksiazek
- usuwanie ksiazek
- modyfikacja tytulu/roku napisania/ilosci stron w ksiazce
- dodawanie klientów,
- listowanie klientow
- usuwanie klientow
- modyfikacja imienia/nazwiska lub numeru id klienta