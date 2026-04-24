package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.ParkingTicket;

public class InMemoryParkingTicketRepository implements IParkingTicketRepository {
    
    private Map<Integer, ParkingTicket> parkingTickets = new HashMap<>();
    private Integer nextId = 1;

    @Override
    public ParkingTicket save(ParkingTicket parkingTicket) {
        if (parkingTicket == null) {
            throw new IllegalArgumentException("Ticket is required.");
        }

        if (parkingTicket.getId() == null) {
            parkingTicket.setId(nextId);
            nextId++;
        }

        parkingTickets.put(parkingTicket.getId(), parkingTicket);
        return parkingTicket;
    }

    @Override
    public Optional<ParkingTicket> findById(Integer id) {
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
