package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

public class Vehicle {

    private static final int MAX = 120;
    private static final double AC = 10;

    // Atributos caracteristicos
    private String brand;
    private String plate;
    private String model;
    private int power;
    private int seats;
    private ColorEnum color;
    private MotorEnum engine;
    private String location;
    private double acelerate;
    private double brakes;
    private double velociti; // Velocidad actual
    private String fabricationDate; // Fecha de fabricacion
    private double weight;

    public Vehicle() {
        this.velociti = 0;
        this.fabricationDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Asigna la fecha actual
    }

    public Vehicle(String brand, String plate, String model, int power, int seats, ColorEnum color, MotorEnum engine, String location, double acelerate, double brakes, String fabricationDate, double weight) {
        this.brand = brand;
        this.plate = plate;
        this.model = model;
        this.power = power;
        this.seats = seats;
        this.color = color;
        this.engine = engine;
        this.location = location;
        setAcelerate(acelerate);
        setBraking(brakes);
        setVelociti(acelerate - brakes);

        // Si la fecha de fabricación no es proporcionada, usa la actual
        if (fabricationDate == null || fabricationDate.isEmpty()) {
            this.fabricationDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            this.fabricationDate = fabricationDate;
        }
    }

public Vehicle(String brand, String model, int power, int seats, ColorEnum color, MotorEnum engine) {
    this.brand = brand;
    this.model = model;
    this.power = power;
    this.seats = seats;
    this.color = color;
    this.engine = engine;

    // Asigna una fecha de fabricación por defecto
    this.fabricationDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
}



    // Metodos getter y setter
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

    public void setAcelerate(double acelerate) {
        this.acelerate = Math.min(Math.max(acelerate, 0), MAX);
    }

    public void setVelociti(double velocity) {
        this.velociti = Math.max(acelerate - brakes, 0);
    }

    public double getAcelerate() {
        return acelerate;
    }

    public double getVelociti() {
        return velociti;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    

    // Metodos de comportamiento
    public void acelerar(float num) {
        this.velociti += num;
        if (this.velociti > MAX) {
            this.velociti = MAX;
        }
        System.out.println("Velocidad actual despues de acelerar: " + this.velociti + " Km/h");
    }

    public void frenar(float num) {
        this.velociti -= num;
        if (this.velociti < 0) {
            this.velociti = 0;
        }
        System.out.println("Velocidad actual despues de frenar: " + this.velociti + " Km/h");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ini-Car: \n");

        if (brand != null) {
            sb.append("Marca: ").append(brand).append("\n");
        }
        if (plate != null) {
            sb.append("Placa: ").append(plate).append("\n");
        }
        if (model != null) {
            sb.append("Modelo: ").append(model).append("\n");
        }
        if (power > 0) {
            sb.append("Potencia: ").append(power).append(" HP\n");
        }
        if (seats > 0) {
            sb.append("Asientos: ").append(seats).append("\n");
        }
        if (color != null) {
            sb.append("Color: ").append(color).append("\n");
        }
        if (engine != null) {
            sb.append("Motor: ").append(engine).append("\n");
        }
        if (location != null) {
            sb.append("Ubicacion: ").append(location).append("\n");
        }
        if (acelerate > 0) {
            sb.append("Aceleracion: ").append(acelerate).append("\n");
        }
        if (brakes > 0) {
            sb.append("Frenado: ").append(brakes).append("\n");
        }
        sb.append("Velocidad actual: ").append(velociti).append(" Km/h\n");
        sb.append("Fecha de fabricacion: ").append(fabricationDate).append("\n");
        sb.append("Antiguedad: ").append(calculateSeniority()).append(" years\n");
        return sb.toString();
        
    }

    // Metodos y Prototipos
    public int calculateSeniority() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Period p = Period.between(LocalDate.parse(fabricationDate, dateTimeFormatter), currentDate);
        return p.getYears();
    }
    
    
    public double CalculateConsuption  (){
        return (1);
    }
    

}
