package pricing;

import model.ParkingTicket;

import java.time.Duration;
import java.time.LocalDateTime;

public class NightDiscountPricingStrategy implements PricingStrategy {
    private Double hourlyRate;
    private Double discountPercent;

    public NightDiscountPricingStrategy(Double hourlyRate, Double discountPercent) {
        if (hourlyRate == null || hourlyRate <= 0) {
            throw new IllegalArgumentException("Hourly rate must be positive.");
        }

        if (discountPercent == null || discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
        }

        this.hourlyRate = hourlyRate;
        this.discountPercent = discountPercent;
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

        long minutes = Duration.between(ticket.getEntryTime(), exitTime).toMinutes();
        long hours = (long) Math.ceil(minutes / 60.0);

        if (hours == 0) {
            hours = 1;
        }

        Double price = hours * hourlyRate;

        if (ticket.getEntryTime().getHour() >= 22 || ticket.getEntryTime().getHour() < 6) {
            price = price - price * discountPercent / 100;
        }

        return price;
    }
}
