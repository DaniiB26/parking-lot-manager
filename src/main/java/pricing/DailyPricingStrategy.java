package pricing;

import model.ParkingTicket;

import java.time.Duration;
import java.time.LocalDateTime;

public class DailyPricingStrategy implements PricingStrategy {
    private Double dailyRate;

    public DailyPricingStrategy(Double dailyRate) {
        if (dailyRate == null || dailyRate <= 0) {
            throw new IllegalArgumentException("Daily rate must be positive.");
        }

        this.dailyRate = dailyRate;
    }

    @Override
    public Double calculatePrice(ParkingTicket ticket, LocalDateTime exitTime) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket is required.");
        }

        if (exitTime == null) {
            throw new IllegalArgumentException("Exit time is required.");
        }

        if (exitTime.isBefore(ticket.getEntryTime())) {
            throw new IllegalArgumentException("Exit time cannot be before entry time.");
        }

        long hours = Duration.between(ticket.getEntryTime(), exitTime).toHours();
        long days = (long) Math.ceil(hours / 24.0);

        if (days == 0) {
            days = 1;
        }

        return days * dailyRate;
    }
}
