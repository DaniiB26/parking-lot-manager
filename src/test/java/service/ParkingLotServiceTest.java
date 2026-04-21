package service;

import model.ParkingTicket;
import org.testng.annotations.Test;
import pricing.HourlyPricingStrategy;
import repository.InMemoryParkingTicketRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.expectThrows;

public class ParkingLotServiceTest {

    @Test
    public void enterParking_validLicensePlate_returnsTicket() {
        InMemoryParkingTicketRepository repository = new InMemoryParkingTicketRepository();
        ParkingLotService service = new ParkingLotService(repository, new HourlyPricingStrategy(10.0));

        ParkingTicket ticket = service.enterParking("B 500 XYZ");

        assertNotNull(ticket);
        assertEquals(ticket.getVehicle().getLicensePlate(), "B 500 XYZ");
        assertEquals(ticket.getStateName(), "ACTIVE");
    }

    @Test
    public void enterParking_validLicensePlate_savesTicketInRepository() {
        InMemoryParkingTicketRepository repository = new InMemoryParkingTicketRepository();
        ParkingLotService service = new ParkingLotService(repository, new HourlyPricingStrategy(10.0));

        ParkingTicket ticket = service.enterParking("B 600 ABC");

        assertEquals(repository.findAll().size(), 1);
        assertEquals(repository.findById(ticket.getId()).get(), ticket);
    }

    @Test
    public void calculatePrice_existingTicket_returnsPrice() {
        InMemoryParkingTicketRepository repository = new InMemoryParkingTicketRepository();
        ParkingLotService service = new ParkingLotService(repository, new HourlyPricingStrategy(10.0));
        ParkingTicket ticket = service.enterParking("B 700 DEF");

        LocalDateTime exitTime = ticket.getEntryTime().plusMinutes(90);

        Double price = service.calculatePrice(ticket.getId(), exitTime);

        assertEquals(price, Double.valueOf(20.0));
    }

    @Test
    public void calculatePrice_unknownTicket_throwsException() {
        InMemoryParkingTicketRepository repository = new InMemoryParkingTicketRepository();
        ParkingLotService service = new ParkingLotService(repository, new HourlyPricingStrategy(10.0));

        expectThrows(
                IllegalArgumentException.class,
                () -> service.calculatePrice(UUID.randomUUID(), LocalDateTime.now())
        );
    }

    @Test
    public void payTicket_existingActiveTicket_changesStateToPaid() {
        InMemoryParkingTicketRepository repository = new InMemoryParkingTicketRepository();
        ParkingLotService service = new ParkingLotService(repository, new HourlyPricingStrategy(10.0));
        ParkingTicket ticket = service.enterParking("B 800 GHI");

        ParkingTicket paidTicket = service.payTicket(ticket.getId());

        assertEquals(paidTicket.getStateName(), "PAID");
    }

    @Test
    public void closeTicket_paidTicket_changesStateToClosed() {
        InMemoryParkingTicketRepository repository = new InMemoryParkingTicketRepository();
        ParkingLotService service = new ParkingLotService(repository, new HourlyPricingStrategy(10.0));
        ParkingTicket ticket = service.enterParking("B 900 JKL");

        service.payTicket(ticket.getId());
        ParkingTicket closedTicket = service.closeTicket(ticket.getId());

        assertEquals(closedTicket.getStateName(), "CLOSED");
    }

    @Test
    public void closeTicket_activeTicket_throwsException() {
        InMemoryParkingTicketRepository repository = new InMemoryParkingTicketRepository();
        ParkingLotService service = new ParkingLotService(repository, new HourlyPricingStrategy(10.0));
        ParkingTicket ticket = service.enterParking("B 111 MMM");

        expectThrows(IllegalStateException.class, () -> service.closeTicket(ticket.getId()));
    }

    @Test
    public void payTicket_unknownTicket_throwsException() {
        InMemoryParkingTicketRepository repository = new InMemoryParkingTicketRepository();
        ParkingLotService service = new ParkingLotService(repository, new HourlyPricingStrategy(10.0));

        expectThrows(IllegalArgumentException.class, () -> service.payTicket(UUID.randomUUID()));
    }

    @Test
    public void getAllTickets_afterEnteringTwoVehicles_returnsTwoTickets() {
        InMemoryParkingTicketRepository repository = new InMemoryParkingTicketRepository();
        ParkingLotService service = new ParkingLotService(repository, new HourlyPricingStrategy(10.0));

        service.enterParking("B 222 NNN");
        service.enterParking("B 333 OOO");

        List<ParkingTicket> tickets = service.getAllTickets();

        assertEquals(tickets.size(), 2);
    }
}
