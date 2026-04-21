package state;

import model.ParkingTicket;

public class ActiveTicketState implements TicketState {

    @Override
    public void pay(ParkingTicket ticket) {
        ticket.setState(new PaidTicketState());
    }

    @Override
    public void close(ParkingTicket ticket) {
        throw new IllegalStateException("Ticket must be paid before closing.");
    }

    @Override
    public String getName() {
        return "ACTIVE";
    }
}
