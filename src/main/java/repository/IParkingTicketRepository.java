package repository;

import java.util.List;
import java.util.Optional;

import model.ParkingTicket;

public interface IParkingTicketRepository {

    public ParkingTicket save(ParkingTicket parkingTicket);
    public Optional<ParkingTicket> findById(Integer id);
    public List<ParkingTicket> findAll();
}
