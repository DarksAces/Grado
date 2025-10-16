package view.console;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import model.Car;
import model.Motorcicle;
import model.Truk;
import model.ColorEnum;
import model.MotorEnum;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Car> listaCoches = new ArrayList<>();
        ArrayList<Motorcicle> listaMotos = new ArrayList<>();
        ArrayList<Truk> listaCamiones = new ArrayList<>();

        // Creación del coche
        Car car1 = new Car("Ford", "Mustang", 450, 4, ColorEnum.RED, MotorEnum.GASOLINA, "", 0 );
        car1.setWeight(1500);
        listaCoches.add(car1);

        // Creación de la moto
        Motorcicle moto1 = new Motorcicle("Yamaha", "R1", 200, 2, ColorEnum.BLUE, MotorEnum.GASOLINA, "10/03/2022");
        moto1.setSidecar(false);
        moto1.setDisplacement(1000);
        listaMotos.add(moto1);

        // Creación del camión
        Truk truck1 = new Truk("Mercedes", "Actros", 510, 2, ColorEnum.BLACK, MotorEnum.DIESEL, "15/06/2020");
        truck1.setCargaMaxima(20000);
        listaCamiones.add(truck1);

        // Mostrar listas antes de eliminación
        System.out.println("Lista de Coches:");
        for (Car coche : listaCoches) {
            System.out.println(coche);
        }

        System.out.println("Lista de Motos:");
        for (Motorcicle moto : listaMotos) {
            System.out.println(moto);
        }

        System.out.println("Lista de Camiones:");
        for (Truk camion : listaCamiones) {
            System.out.println(camion);
        }

        // Eliminar coche "Mustang"
        Iterator<Car> iterator = listaCoches.iterator();
        while (iterator.hasNext()) {
            Car coche = iterator.next();
            if ("Ford".equals(coche.getBrand()) && "Mustang".equals(coche.getModel())) {
                System.out.println("Coche eliminado: " + coche);
                iterator.remove();
                break;
            }
        }

        // Mostrar lista después de eliminación
        System.out.println("Lista final de Coches:");
        for (Car coche : listaCoches) {
            System.out.println(coche);
        }

        // Calcular consumo de cada vehículo
        System.out.println("Consumo del coche: " + CalculateConsuption(car1) + " L/100km");
        System.out.println("Consumo de la moto: " + CalculateConsuption(moto1) + " L/100km");
        System.out.println("Consumo del camion: " + CalculateConsuption(truck1) + " L/100km");
    }

    private static double CalculateConsuption(Car c) {
        return c.CalculateConsuption();
    }

    private static double CalculateConsuption(Motorcicle m) {
        return m.CalculateConsuption();
    }

    private static double CalculateConsuption(Truk t) {
        return t.CalculateConsuption();
    }
}
