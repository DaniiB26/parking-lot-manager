package pricing;

import model.ParkingTicket;
import model.Vehicle;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;

public class HourlyPricingStrategyTest {

    @Test
    public void calculatePrice_oneHourAndThirtyMinutes_returnsTwoHoursPrice() {
        Vehicle vehicle = new Vehicle("B 123 ABC");
        ParkingTicket ticket = new ParkingTicket(vehicle, LocalDateTime.of(2026, 4, 20, 10, 0));

        PricingStrategy pricingStrategy = new HourlyPricingStrategy(10);

        double price = pricingStrategy.calculatePrice(ticket, LocalDateTime.of(2026, 4, 20, 11, 30));

        assertEquals(price, 20.0);
    }

    @Test
    public void calculatePrice_lessThanOneHour_returnsOneHourPrice() {
        Vehicle vehicle = new Vehicle("B 456 XYZ");
        ParkingTicket ticket = new ParkingTicket(
                vehicle,
                LocalDateTime.of(2026, 4, 20, 10, 0)
        );

        PricingStrategy pricingStrategy = new HourlyPricingStrategy(10);

        double price = pricingStrategy.calculatePrice(
                ticket,
                LocalDateTime.of(2026, 4, 20, 10, 25)
        );

        assertEquals(price, 10.0);
    }
}
