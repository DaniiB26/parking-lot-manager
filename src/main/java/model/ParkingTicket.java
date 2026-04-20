package model;

import java.time.LocalDateTime;
import java.util.UUID;

public class ParkingTicket {
    private UUID id;
    private Vehicle vehicle;
    private LocalDateTime entryTime;

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
    }

    public UUID getId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }
}
