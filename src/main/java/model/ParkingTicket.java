package model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;
import state.ActiveTicketState;
import state.TicketState;

@Getter
public class ParkingTicket {
    private UUID id;
    private Vehicle vehicle;
    private LocalDateTime entryTime;
    private TicketState state;


    public ParkingTicket(Vehicle vehicle, LocalDateTime entryTime) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle is required.");
        }

        if (entryTime == null) {
            throw new IllegalArgumentException("Entry time is required.");
        }

        this.id = UUID.randomUUID();
        this.vehicle = vehicle;
        this.entryTime = entryTime;
        this.state = new ActiveTicketState();
    }

    public void pay() {
        state.pay(this);
    }

    public void close() {
        state.close(this);
    }

    public String getStateName() {
        return state.getName();
    }

    public void setState(TicketState state) {
        this.state = state;
    }
}
