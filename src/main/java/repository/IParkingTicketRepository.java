package repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import model.ParkingTicket;

public interface IParkingTicketRepository {

    public ParkingTicket save(ParkingTicket parkingTicket);
    public Optional<ParkingTicket> findById(UUID id);
    public List<ParkingTicket> findAll();
}
