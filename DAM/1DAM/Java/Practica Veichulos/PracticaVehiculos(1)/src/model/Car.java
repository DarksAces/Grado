package model;


public class Car extends Vehicle {

    private int numDoors;

    public Car() {
        super();
    }

    public Car(String brand, String plate, String model, int power, int seats, ColorEnum color, MotorEnum engine,
            String location, double acelerate, double brakes, int numDoors, String fabricationDate, double weight) {
        super(brand, plate, model, power, seats, color, engine, location, acelerate, brakes, fabricationDate, weight);
        this.numDoors = numDoors;
    }

    public Car(String brand, String plate, String model, int power, int seats, ColorEnum color, MotorEnum engine,
            double weight) {
        super(brand, plate, model, power, seats, color, engine);
        this.numDoors = 4;
        this.setWeight(weight);
    }

    public int getNumDoors() {
        return numDoors;
    }

    public void setNumDoors(int numDoors) {
        this.numDoors = numDoors;
    }

    @Override
    public String toString() {
        return super.toString() + "Número de puertas: " + numDoors + "\n";
    }
}
