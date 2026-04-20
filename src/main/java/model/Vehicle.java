package model;

public class Vehicle {
    private String licensePlate;

    public Vehicle(String licensePlate) {
        if (licensePlate == null || licensePlate.isBlank()) {
            throw new IllegalArgumentException("License plate is required.");
        }

        this.licensePlate = licensePlate.trim().toUpperCase();
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}
