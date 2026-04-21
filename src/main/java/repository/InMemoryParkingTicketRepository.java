package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import model.ParkingTicket;

public class InMemoryParkingTicketRepository implements IParkingTicketRepository {
    
    private Map<UUID, ParkingTicket> parkingTickets = new HashMap<>();

    @Override
    public ParkingTicket save(ParkingTicket parkingTicket) {
        if (parkingTicket == null) {
            throw new IllegalArgumentException("Ticket is required.");
        }

        parkingTickets.put(parkingTicket.getId(), parkingTicket);
        return parkingTicket;
    }

    @Override
    public Optional<ParkingTicket> findById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is required.");
        }

        return Optional.ofNullable(parkingTickets.get(id));
    }

    @Override
    public List<ParkingTicket> findAll() {
        return new ArrayList<>(parkingTickets.values());
    }
}
