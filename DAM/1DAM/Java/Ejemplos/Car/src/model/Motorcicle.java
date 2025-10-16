package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Motorcicle extends Vehicle {

    private boolean sidecar;
    private int displacement;

    public Motorcicle() {
        super();
        this.sidecar = false;
        this.displacement = 0;
    }

    public Motorcicle(String brand, String model, int power, int seats, ColorEnum color, MotorEnum engine, String fabricationDate) {
        super(brand, model, power, seats, color, engine);

        if (fabricationDate == null || fabricationDate.isEmpty()) {
            this.setFabricationDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            this.setFabricationDate(fabricationDate);
        }

        this.sidecar = false;
        this.displacement = 500;
    }

    public boolean hasSidecar() {
        return sidecar;
    }

    public void setSidecar(boolean sidecar) {
        this.sidecar = sidecar;
    }

    public int getDisplacement() {
        return displacement;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    @Override
    public String toString() {
        return super.toString() + "\nTiene sidecar? " + (sidecar ? "Sí" : "No") + "\nCilindrada: " + displacement + " cc\n";
    }
}
