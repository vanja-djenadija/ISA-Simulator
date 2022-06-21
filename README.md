# Simulator arhitekture instrukcionog skupa

## Projektni zadatak 1 - Arhitektura računara

<p align="justify">
(10) U proizvoljnom programskom jeziku napisati simulator sopstvene arhitekture instrukcionog skupa
sa bar 4 registra opšte namjene dužine 64 bita (dozvoljeno je koristiti tip podataka dužine 8 bajta, npr.
long long ili uint64_t za registre). Simulator treba da funkcioniše kao interpreter asemblerskih
instrukcija. Treba biti omogućeno da se izvorni asemblerski kod učita iz fajla. Pravilno izvršiti potrebnu
sintaksičku i semantičku analizu koda u svrhu detekcije nevalidnih dijelova koda. Instrukcijski skup
simulirane mašine (gosta) mora da obuhvata:</p>

- osnovne aritmetičke operacije (ADD, SUB)
- osnovne bitske logičke operacije (AND, OR, NOT)
- instrukciju za pomjeranje podataka između registara (MOV)
- instrukciju za unos podataka sa standardnog ulaza (slično odgovarajućem sistemskom pozivu)
- instrukciju za ispis podataka na standardni izlaz (slično odgovarajućem sistemskom pozivu)

<p align="justify">
(5) Implementirati rad sa memorijom. Simulirana mašina (gost) treba da ima 64-bitni adresni prostor.
Omogućiti direktno i indirektno adresiranje. Sadržaj svake memorijske adrese treba biti dužine 1 bajt.
Pravilno omogućiti da se adrese mogu navesti kao brojevi. Omogućiti pristup svim adresama iz adresnog
prostora, uključujući i upis i čitanje, upotrebom odgovarajućih instrukcija (MOV ili LOAD/STORE).
</p>

(5) Implementirati instrukcije potrebne za bezuslovno i uslovno grananje (JMP, CMP, JE, JNE, JGE, JL).

<p align="justify">
(2) Implementirati jednostavnu single-step debugging podršku. Omogućiti izvršavanje i pregled
vrijednosti svih registara i specificiranih memorijskih adresa na postavljenim zaustavnim tačkama u
asemblerskom kodu tokom izvršavanja. U ovom režimu treba biti omogućen prelaz na sljedeću
instrukciju (NEXT ili STEP konzolne komande) i prelaz na sljedeću zaustavnu tačku (CONTINUE).
</p>
<p align="justify">
(3) Omogućiti da se asemblerske instrukcije mogu prevesti u mašinski kod gosta i smjestiti na proizvoljnu
lokaciju u adresni prostor gosta (analogno code ili text segmentu). Definisati i iskoristiti registar koji će
da služi kao programski brojač (instrukcijski pokazivač). Omogućiti da mašina radi u režimu u kom se
izvršavaju mašinske instrukcije locirane u memoriji gosta. Prelazak u režim u kom se izvršavaju
instrukcije iz memorije se može izvršiti upotrebom namjenske instrukcije. Konstruisati primjer
asemblerskog koda za gosta koji je samomodifikujući (nakon prevođenja u mašinski kod gosta) i u kom
se modifikovani dio koda izvršava i prije i poslije relevantne modifikacije.

Obezbijediti nekoliko jediničnih testova kojima se demonstriraju funkcionalnosti iz stavki u specifikaciji projektnog zadatka.
Sve detalje koji nisu eksplicitno navedeni implementirati na proizvoljan način.

</p>
Pridržavati se:

- principa objektno-orijentisanog programiranja i SOLID principa
- principa pisanja čistog koda i pravilnog imenovanja varijabli, funkcija, klasa i metoda
- konvencija za korišteni programski jezi
