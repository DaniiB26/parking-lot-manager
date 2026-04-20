package pricing;

import model.ParkingTicket;

import java.time.Duration;
import java.time.LocalDateTime;

public class HourlyPricingStrategy implements PricingStrategy {
    private double hourlyRate;

    public HourlyPricingStrategy(double hourlyRate) {
        if (hourlyRate <= 0) {
            throw new IllegalArgumentException("Hourly rate must be positive.");
        }

        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculatePrice(ParkingTicket ticket, LocalDateTime exitTime) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket is required.");
        }

        if (exitTime == null) {
            throw new IllegalArgumentException("Exit time is required.");
        }

        if (exitTime.isBefore(ticket.getEntryTime())) {
            throw new IllegalArgumentException("Exit time cannot be before entry time.");
        }

        long minutes = Duration.between(ticket.getEntryTime(), exitTime).toMinutes();
        long hours = (long) Math.ceil(minutes / 60.0);

        if (hours == 0) {
            hours = 1;
        }

        return hours * hourlyRate;
    }
}
