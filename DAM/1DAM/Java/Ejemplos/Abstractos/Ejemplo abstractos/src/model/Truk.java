package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Truk extends Vehicle {

    private double maxLoad;

    // Constructor without parameters
    public Truk() {
        super();  // Calls the no-parameter constructor of Vehicle
        this.maxLoad = 0;  // Initializes maxLoad
    }

    // Constructor with basic parameters
    public Truk(String brand, String plate, String model, int power, int seats, ColorEnum color, MotorEnum engine, 
                 String fabricationDate, double maxLoad) {
        super(brand, plate, model, power, seats, color, engine);
        this.maxLoad = maxLoad;

        // If no fabrication date is provided, assigns the current date
        if (fabricationDate == null || fabricationDate.isEmpty()) {
            this.setFabricationDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            this.setFabricationDate(fabricationDate);
        }
    }

    // More complete constructor
    public Truk(String brand, String plate, String model, int power, int seats, ColorEnum color, MotorEnum engine, 
                 String location, double accelerate, double brakes, String fabricationDate, double weight, double maxLoad) {
        super(brand, plate, model, power, seats, color, engine, location, accelerate, brakes, fabricationDate, weight);
        this.maxLoad = maxLoad;
    }

    // Getter and setter methods
    public double getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(double maxLoad) {
        this.maxLoad = maxLoad;
    }

    @Override
    public String toString() {
        return super.toString() + "Maximum load: " + maxLoad + " kg\n";
    }
}
