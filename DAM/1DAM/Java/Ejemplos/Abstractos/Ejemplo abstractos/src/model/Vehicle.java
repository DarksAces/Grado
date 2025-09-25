package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

public class Vehicle {

    private static final int MAX = 120;
    private static final double AC = 10;

    // Characteristic attributes
    private String brand;
    private String plate;
    private String model;
    private int power;
    private int seats;
    private ColorEnum color;
    private MotorEnum engine;
    private String location;
    private double accelerate;
    private double brakes;
    private double velocity; // Current speed
    private String fabricationDate; // Manufacturing date
    private double weight;

    public Vehicle() {
        this.velocity = 0;
        this.fabricationDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Assigns the current date
    }

    public Vehicle(String brand, String plate, String model, int power, int seats, ColorEnum color, MotorEnum engine, String location, double accelerate, double brakes, String fabricationDate, double weight) {
        this.brand = brand;
        this.plate = plate;
        this.model = model;
        this.power = power;
        this.seats = seats;
        this.color = color;
        this.engine = engine;
        this.location = location;
        setAcelerate(accelerate);
        setBraking(brakes);
        setVelocity(accelerate - brakes);

        // If the fabrication date is not provided, use the current date
        if (fabricationDate == null || fabricationDate.isEmpty()) {
            this.fabricationDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            this.fabricationDate = fabricationDate;
        }
    }

    public Vehicle(String brand, String plate, String model, int power, int seats, ColorEnum color, MotorEnum engine) {
        this.brand = brand;
        this.plate = plate;
        this.model = model;
        this.power = power;
        this.seats = seats;
        this.color = color;
        this.engine = engine;
        this.fabricationDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    // Getter and setter methods
    public String getFabricationDate() {
        return fabricationDate;
    }

    public void setFabricationDate(String fabricationDate) {
        this.fabricationDate = fabricationDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getBrakes() {
        return brakes;
    }

    public void setBrakes(double brakes) {
        this.brakes = Math.max(brakes, 0);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public ColorEnum getColor() {
        return color;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }

    public MotorEnum getEngine() {
        return engine;
    }

    public void setEngine(MotorEnum engine) {
        this.engine = engine;
    }

    public void setBraking(double brakes) {
        this.brakes = Math.max(brakes, 0);
    }

    public void setAcelerate(double accelerate) {
        this.accelerate = Math.min(Math.max(accelerate, 0), MAX);
    }

    public void setVelocity(double velocity) {
        this.velocity = Math.max(accelerate - brakes, 0);
    }

    public double getAcelerate() {
        return accelerate;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    // Behavior methods
    public void accelerate(float num) {
        this.velocity += num;
        if (this.velocity > MAX) {
            this.velocity = MAX;
        }
        System.out.println("Current speed after accelerating: " + this.velocity + " Km/h");
    }

    public void brake(float num) {
        this.velocity -= num;
        if (this.velocity < 0) {
            this.velocity = 0;
        }
        System.out.println("Current speed after braking: " + this.velocity + " Km/h");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vehicle: \n");

        if (brand != null) {
            sb.append("Brand: ").append(brand).append("\n");
        }
        if (plate != null) {
            sb.append("Plate: ").append(plate).append("\n");
        }
        if (model != null) {
            sb.append("Model: ").append(model).append("\n");
        }
        if (power > 0) {
            sb.append("Power: ").append(power).append(" HP\n");
        }
        if (seats > 0) {
            sb.append("Seats: ").append(seats).append("\n");
        }
        if (color != null) {
            sb.append("Color: ").append(color).append("\n");
        }
        if (engine != null) {
            sb.append("Engine: ").append(engine).append("\n");
        }
        if (location != null) {
            sb.append("Location: ").append(location).append("\n");
        }
        if (accelerate > 0) {
            sb.append("Acceleration: ").append(accelerate).append("\n");
        }
        if (brakes > 0) {
            sb.append("Braking: ").append(brakes).append("\n");
        }
        sb.append("Current speed: ").append(velocity).append(" Km/h\n");
        sb.append("Manufacturing date: ").append(fabricationDate).append("\n");
        sb.append("Age: ").append(calculateSeniority()).append(" years\n");
        return sb.toString();
    }

    // Methods and Prototypes
    public int calculateSeniority() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Period p = Period.between(LocalDate.parse(fabricationDate, dateTimeFormatter), currentDate);
        return p.getYears();
    }

    public double calculateConsumption() {
        return 1;
    }
}
