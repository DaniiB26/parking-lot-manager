package state;

import model.ParkingTicket;

public class ClosedTicketState implements TicketState {

    @Override
    public void pay(ParkingTicket ticket) {
        throw new IllegalStateException("Closed ticket cannot be paid.");
    }

    @Override
    public void close(ParkingTicket ticket) {
        throw new IllegalStateException("Ticket is already closed.");
    }

    @Override
    public String getName() {
        return "CLOSED";
    }
}
