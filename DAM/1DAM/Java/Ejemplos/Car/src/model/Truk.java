package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Truk extends Vehicle {

    private double cargaMaxima;

    public Truk() {
        super();
        this.cargaMaxima = 0;
    }

    public Truk(String brand, String model, int power, int seats, ColorEnum color, MotorEnum engine, String fabricationDate) {
        super(brand, model, power, seats, color, engine);

        if (fabricationDate == null || fabricationDate.isEmpty()) {
            this.setFabricationDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            this.setFabricationDate(fabricationDate);
        }

        this.cargaMaxima = 10000;
    }

    public double getCargaMaxima() {
        return cargaMaxima;
    }

    public void setCargaMaxima(double cargaMaxima) {
        this.cargaMaxima = cargaMaxima;
    }

    @Override
    public String toString() {
        return super.toString() + "\nCarga máxima: " + cargaMaxima + " kg\n";
    }
}
