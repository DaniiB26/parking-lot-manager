package repository;

import model.ParkingTicket;
import model.Vehicle;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.expectThrows;

public class InMemoryParkingTicketRepositoryTest {

    @Test
    public void save_validTicket_canBeFoundById() {
        InMemoryParkingTicketRepository repository = new InMemoryParkingTicketRepository();
        ParkingTicket ticket = createTicket("B 100 AAA");

        repository.save(ticket);

        Optional<ParkingTicket> foundTicket = repository.findById(ticket.getId());

        assertNotNull(foundTicket);
        assertSame(foundTicket.get(), ticket);
    }

    @Test
    public void findById_unknownId_returnsEmptyOptional() {
        InMemoryParkingTicketRepository repository = new InMemoryParkingTicketRepository();

        Optional<ParkingTicket> foundTicket = repository.findById(UUID.randomUUID());

        assertFalse(foundTicket.isPresent());
    }

    @Test
    public void findAll_afterSavingTwoTickets_returnsTwoTickets() {
        InMemoryParkingTicketRepository repository = new InMemoryParkingTicketRepository();

        repository.save(createTicket("B 200 BBB"));
        repository.save(createTicket("B 300 CCC"));

        assertEquals(repository.findAll().size(), 2);
    }

    @Test
    public void save_nullTicket_throwsException() {
        InMemoryParkingTicketRepository repository = new InMemoryParkingTicketRepository();

        expectThrows(IllegalArgumentException.class, () -> repository.save(null));
    }

    private ParkingTicket createTicket(String licensePlate) {
        Vehicle vehicle = new Vehicle(licensePlate);
        return new ParkingTicket(vehicle, LocalDateTime.of(2026, 4, 20, 10, 0));
    }
}
