package service;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import model.ParkingTicket;
import model.Vehicle;
import pricing.PricingStrategy;
import repository.IParkingTicketRepository;

@AllArgsConstructor
public class ParkingLotService {
    
    private IParkingTicketRepository parkingTicketRepository;
    private PricingStrategy pricingStrategy;

    public ParkingTicket enterParking(String licensePlate) {
        Vehicle vehicle = new Vehicle(licensePlate);
        ParkingTicket ticket = new ParkingTicket(vehicle, LocalDateTime.now());

        return parkingTicketRepository.save(ticket);
    }

    public Double calculatePrice(Integer ticketId, LocalDateTime exitTime) {
        ParkingTicket ticket = getTicketOrThrow(ticketId);

        return pricingStrategy.calculatePrice(ticket, exitTime);
    }

    public ParkingTicket payTicket(Integer ticketId) {
        ParkingTicket ticket = getTicketOrThrow(ticketId);

        ticket.pay();

        return parkingTicketRepository.save(ticket);
    }

    public ParkingTicket closeTicket(Integer ticketId) {
        ParkingTicket ticket = getTicketOrThrow(ticketId);

        ticket.close();

        return parkingTicketRepository.save(ticket);
    }

    public List<ParkingTicket> getAllTickets() {
        return parkingTicketRepository.findAll();
    }

    private ParkingTicket getTicketOrThrow(Integer ticketId) {
        return parkingTicketRepository
                .findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found!"));
    }
}
