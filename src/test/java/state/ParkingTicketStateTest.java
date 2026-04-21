package state;

import model.ParkingTicket;
import model.Vehicle;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

public class ParkingTicketStateTest {

    @Test
    public void newTicket_hasActiveState() {
        ParkingTicket ticket = new ParkingTicket(
                new Vehicle("B 100 AAA"),
                LocalDateTime.of(2026, 4, 20, 10, 0)
        );

        assertEquals(ticket.getStateName(), "ACTIVE");
    }

    @Test
    public void pay_activeTicket_changesStateToPaid() {
        ParkingTicket ticket = new ParkingTicket(
                new Vehicle("B 200 BBB"),
                LocalDateTime.of(2026, 4, 20, 10, 0)
        );

        ticket.pay();

        assertEquals(ticket.getStateName(), "PAID");
    }

    @Test
    public void close_paidTicket_changesStateToClosed() {
        ParkingTicket ticket = new ParkingTicket(
                new Vehicle("B 300 CCC"),
                LocalDateTime.of(2026, 4, 20, 10, 0)
        );

        ticket.pay();
        ticket.close();

        assertEquals(ticket.getStateName(), "CLOSED");
    }

    @Test
    public void close_activeTicket_throwsException() {
        ParkingTicket ticket = new ParkingTicket(
                new Vehicle("B 400 DDD"),
                LocalDateTime.of(2026, 4, 20, 10, 0)
        );

        expectThrows(IllegalStateException.class, ticket::close);
    }
}
