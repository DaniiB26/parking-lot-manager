package pricing;

import model.ParkingTicket;
import model.Vehicle;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;

public class NightDiscountPricingStrategyTest {

    @Test
    public void calculatePrice_nightEntry_appliesDiscount() {
        ParkingTicket ticket = new ParkingTicket(
                new Vehicle("B 333 CCC"),
                LocalDateTime.of(2026, 4, 20, 23, 0)
        );
        PricingStrategy strategy = new NightDiscountPricingStrategy(10.0, 50.0);

        Double price = strategy.calculatePrice(ticket, LocalDateTime.of(2026, 4, 21, 1, 0));

        assertEquals(price, Double.valueOf(10.0));
    }

    @Test
    public void calculatePrice_dayEntry_doesNotApplyDiscount() {
        ParkingTicket ticket = new ParkingTicket(
                new Vehicle("B 444 DDD"),
                LocalDateTime.of(2026, 4, 20, 10, 0)
        );
        PricingStrategy strategy = new NightDiscountPricingStrategy(10.0, 50.0);

        Double price = strategy.calculatePrice(ticket, LocalDateTime.of(2026, 4, 20, 12, 0));

        assertEquals(price, Double.valueOf(20.0));
    }
}
