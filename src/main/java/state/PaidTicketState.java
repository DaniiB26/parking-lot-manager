package state;

import model.ParkingTicket;

public class PaidTicketState implements TicketState {

    @Override
    public void pay(ParkingTicket ticket) {
        throw new IllegalStateException("Ticket is already paid.");
    }

    @Override
    public void close(ParkingTicket ticket) {
        ticket.setState(new ClosedTicketState());
    }

    @Override
    public String getName() {
        return "PAID";
    }
}
