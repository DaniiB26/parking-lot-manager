package pricing;

import model.ParkingTicket;

import java.time.LocalDateTime;

public interface PricingStrategy {
    Double calculatePrice(ParkingTicket ticket, LocalDateTime exitTime);
}
