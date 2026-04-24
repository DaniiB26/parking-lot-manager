# Parking Lot Manager

Aplicatie Java pentru administrarea simpla a tichetelor dintr-o parcare.

Aplicatia permite:

- crearea unui tichet pentru o masina
- afisarea tichetelor existente
- calcularea pretului parcarii
- plata unui tichet
- inchiderea unui tichet platit
- alegerea strategiei de calcul a pretului din UI

## Design patterns folosite

### Strategy Pattern

Strategy este folosit pentru calcularea pretului parcarii.

Interfata:

- `pricing.PricingStrategy`

Implementari:

- `pricing.HourlyPricingStrategy`
- `pricing.DailyPricingStrategy`
- `pricing.NightDiscountPricingStrategy`

`ParkingLotService` foloseste interfata `PricingStrategy`, nu o clasa concreta.
Astfel, algoritmul de calcul poate fi schimbat fara modificarea logicii din
service.

### State Pattern

State este folosit pentru starea unui tichet.

Interfata:

- `state.TicketState`
 
Implementari:

- `state.ActiveTicketState`
- `state.PaidTicketState`
- `state.ClosedTicketState`

Un tichet nou este `ACTIVE`. Dupa plata devine `PAID`, iar dupa inchidere devine
`CLOSED`. Tranzitiile invalide, de exemplu inchiderea unui tichet neplatit, sunt
tratate in clasele de state.

## Principii OOP folosite

- incapsulare: datele din clase sunt private si sunt accesate prin metode
- abstractizare: service-ul foloseste interfete precum `PricingStrategy` si`IParkingTicketRepository`
- separarea responsabilitatilor: model, repository, service, pricing, state si ui sunt separate

## Cum se ruleaza

Din folderul proiectului:

```powershell
mvn javafx:run
```

Nu se recomanda rularea cu butonul simplu `Run Java`, deoarece JavaFX are nevoie
de configurarea modulelor prin Maven.

## Cum se ruleaza testele

```powershell
mvn test
```

Proiectul contine teste pentru:

- strategiile de pret
- starile tichetului
- repository-ul in memorie
- service-ul principal

## Tehnologii

- Java 21
- JavaFX
- Maven
- TestNG
- Lombok
- Quarkus dependencies

