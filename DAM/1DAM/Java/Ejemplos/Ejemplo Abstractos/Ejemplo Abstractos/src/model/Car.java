package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Car extends Vehicle {

    private int numDoors;

    public Car() {
        super();
        this.numDoors = 4;
    }

    public Car(String brand, String plate, String model, int power, int seats, ColorEnum color, MotorEnum engine, 
               String location, double acelerate, double brakes, int numDoors, String fabricationDate, double weight) {
        super(brand, plate, model, power, seats, color, engine, location, acelerate, brakes, fabricationDate, weight);
        this.numDoors = numDoors;
    }

    public Car(String brand, String model, int power, int seats, ColorEnum color, MotorEnum engine, 
               String fabricationDate, double weight) {
        super(brand, model, power, seats, color, engine);
        this.numDoors = 4;
        this.setWeight(weight);
        
        this.setFabricationDate((fabricationDate == null || fabricationDate.isEmpty()) 
            ? LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) 
            : fabricationDate);
    }

    public int getNumDoors() {
        return numDoors;
    }

    public void setNumDoors(int numDoors) {
        this.numDoors = numDoors;
    }

    @Override
    public String toString() {
        return super.toString() + "Numero de puertas: " + numDoors + "\n";
    }

    @Override
    public double CalculateConsuption() {
     double consumo = 0;
    consumo = this.getWeight() * 10/100;
        return consumo;
    }
}
