package state;

import model.ParkingTicket;

public interface TicketState {
    void pay(ParkingTicket ticket);

    void close(ParkingTicket ticket);

    String getName();
}
