package model;

import lombok.Getter;

import java.time.LocalDateTime;
import state.ActiveTicketState;
import state.TicketState;

@Getter
public class ParkingTicket {
    private Integer id;
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

    public void changeState(TicketState state) {
        if (state == null) {
            throw new IllegalArgumentException("State is required.");
        }

        this.state = state;
    }
}
