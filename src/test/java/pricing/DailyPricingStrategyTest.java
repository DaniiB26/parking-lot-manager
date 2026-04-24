package pricing;

import model.ParkingTicket;
import model.Vehicle;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;

public class DailyPricingStrategyTest {

    @Test
    public void calculatePrice_lessThanOneDay_returnsOneDayPrice() {
        ParkingTicket ticket = new ParkingTicket(
                new Vehicle("B 111 AAA"),
                LocalDateTime.of(2026, 4, 20, 10, 0)
        );
        PricingStrategy strategy = new DailyPricingStrategy(50.0);

        Double price = strategy.calculatePrice(ticket, LocalDateTime.of(2026, 4, 20, 18, 0));

        assertEquals(price, Double.valueOf(50.0));
    }

    @Test
    public void calculatePrice_moreThanOneDay_returnsTwoDaysPrice() {
        ParkingTicket ticket = new ParkingTicket(
                new Vehicle("B 222 BBB"),
                LocalDateTime.of(2026, 4, 20, 10, 0)
        );
        PricingStrategy strategy = new DailyPricingStrategy(50.0);

        Double price = strategy.calculatePrice(ticket, LocalDateTime.of(2026, 4, 21, 11, 0));

        assertEquals(price, Double.valueOf(100.0));
    }
}
