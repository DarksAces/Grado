package view.console;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import model.Car;
import model.Motorcicle;
import model.Truk;
import model.ColorEnum;
import model.MotorEnum;
import model.Vehicle;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Vehicle> listaVehicle = new ArrayList<>();

        String brand;
        String model;
        String plate;
        String fabricationDate;
        int power;
        int seats;
        int numDoors;
        int displacement;
        double weight;
        boolean validColor;
        boolean validEngine;
        boolean validDate;
        boolean sidecar;
        String selectionPlate;

        do {
            System.out.println("1. Create a car");
            System.out.println("2. Create a motorcycle");
            System.out.println("3. Create a truk");
            System.out.println("4. View list");
            System.out.println("5. Search by plate");
            System.out.println("6. Show total vehicles");
            System.out.println("7. Show total cars");
            System.out.println("8. Show total motorcycles");
            System.out.println("9. Show total truks");
            System.out.println("10. Delete a vehicle by plate");
            System.out.println("Z. Exit the loop");
            String opcion = sc.next().toUpperCase();

            if (opcion.equals("Z")) {
                System.out.println("Exiting the program...");
                break;
            }

            switch (opcion) {
                case "1":
                    System.out.println("Enter the car brand:");
                    brand = sc.next();

                    System.out.println("Enter the car plate:");
                    plate = sc.next();

                    System.out.println("Enter the car model:");
                    model = sc.next();

                    System.out.println("Enter the car power (in horsepower):");
                    power = sc.nextInt();

                    System.out.println("Enter the number of seats:");
                    seats = sc.nextInt();

                    validColor = false;
                    ColorEnum color = null;
                    while (!validColor) {
                        System.out.println("Choose the car color (RED, BLUE, GREEN, BLACK, WHITE, GRAY):");
                        String colorInput = sc.next().toUpperCase();
                        for (ColorEnum c : ColorEnum.values()) {
                            if (c.name().equals(colorInput)) {
                                color = c;
                                validColor = true;
                                break;
                            }
                        }
                        if (!validColor) {
                            System.out.println("Invalid color. Please try again.");
                        }
                    }

                    validEngine = false;
                    MotorEnum engine = null;
                    while (!validEngine) {
                        System.out.println("Choose the engine type (GASOLINE, DIESEL, ELECTRIC, HYBRID):");
                        String engineInput = sc.next().toUpperCase();
                        for (MotorEnum m : MotorEnum.values()) {
                            if (m.name().equals(engineInput)) {
                                engine = m;
                                validEngine = true;
                                break;
                            }
                        }
                        if (!validEngine) {
                            System.out.println("Invalid engine type. Please try again.");
                        }
                    }

                    validDate = false;
                    fabricationDate = "";
                    while (!validDate) {
                        System.out.println("Enter the fabrication date (DD/MM/YYYY):");
                        fabricationDate = sc.next();
                        if (fabricationDate.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                            LocalDate date = LocalDate.parse(fabricationDate, formatter);
                            fabricationDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            validDate = true;
                        } else {
                            System.out.println("Incorrect date format. Use DD/MM/YYYY.");
                        }
                    }

                    System.out.println("Enter the car weight (kg):");
                    weight = sc.nextDouble();

                    System.out.println("Enter the number of doors of the car:");
                    numDoors = sc.nextInt();

                    Car newCar = new Car(brand, plate, model, power, seats, color, engine, weight);
                    newCar.setNumDoors(numDoors);
                    newCar.setFabricationDate(fabricationDate);
                    listaVehicle.add(newCar);
                    System.out.println("Car created successfully.");
                    break;

                case "2":
                    System.out.println("Enter the motorcycle brand:");
                    brand = sc.next();

                    System.out.println("Enter the motorcycle plate:");
                    plate = sc.next();

                    System.out.println("Enter the motorcycle model:");
                    model = sc.next();

                    System.out.println("Enter the motorcycle power (in horsepower):");
                    power = sc.nextInt();

                    System.out.println("Enter the number of seats:");
                    seats = sc.nextInt();

                    validColor = false;
                    color = null;
                    while (!validColor) {
                        System.out.println("Choose the motorcycle color (RED, BLUE, GREEN, BLACK, WHITE, GRAY):");
                        String colorInput = sc.next().toUpperCase();
                        for (ColorEnum c : ColorEnum.values()) {
                            if (c.name().equals(colorInput)) {
                                color = c;
                                validColor = true;
                                break;
                            }
                        }
                        if (!validColor) {
                            System.out.println("Invalid color. Please try again.");
                        }
                    }

                    validEngine = false;
                    engine = null;
                    while (!validEngine) {
                        System.out.println("Choose the engine type (GASOLINE, DIESEL, ELECTRIC, HYBRID):");
                        String engineInput = sc.next().toUpperCase();
                        for (MotorEnum m : MotorEnum.values()) {
                            if (m.name().equals(engineInput)) {
                                engine = m;
                                validEngine = true;
                                break;
                            }
                        }
                        if (!validEngine) {
                            System.out.println("Invalid engine type. Please try again.");
                        }
                    }

                    validDate = false;
                    fabricationDate = "";
                    while (!validDate) {
                        System.out.println("Enter the fabrication date (DD/MM/YYYY):");
                        fabricationDate = sc.next();
                        if (fabricationDate.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                            LocalDate date = LocalDate.parse(fabricationDate, formatter);
                            fabricationDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            validDate = true;
                        } else {
                            System.out.println("Incorrect date format. Use DD/MM/YYYY.");
                        }
                    }

                    System.out.println("Does it have a sidecar? (true/false):");
                    sidecar = sc.nextBoolean();

                    System.out.println("Enter the motorcycle displacement (cc):");
                    displacement = sc.nextInt();

                    Motorcicle newMotorcicle = new Motorcicle(brand, plate, model, power, seats, color, engine, fabricationDate);
                    newMotorcicle.setSidecar(sidecar);
                    newMotorcicle.setDisplacement(displacement);
                    listaVehicle.add(newMotorcicle);
                    System.out.println("Motorcycle created successfully.");
                    break;

                case "3":
                    System.out.println("Enter the truk brand:");
                    brand = sc.next();

                    System.out.println("Enter the truk plate:");
                    plate = sc.next();

                    System.out.println("Enter the truk model:");
                    model = sc.next();

                    System.out.println("Enter the truk power (in horsepower):");
                    power = sc.nextInt();

                    System.out.println("Enter the number of seats:");
                    seats = sc.nextInt();

                    validColor = false;
                    ColorEnum colorTruk = null;
                    while (!validColor) {
                        System.out.println("Choose the truk color (RED, BLUE, GREEN, BLACK, WHITE, GRAY):");
                        String colorInput = sc.next().toUpperCase();
                        for (ColorEnum c : ColorEnum.values()) {
                            if (c.name().equals(colorInput)) {
                                colorTruk = c;
                                validColor = true;
                                break;
                            }
                        }
                        if (!validColor) {
                            System.out.println("Invalid color. Please try again.");
                        }
                    }

                    validEngine = false;
                    MotorEnum engineTruk = null;
                    while (!validEngine) {
                        System.out.println("Choose the engine type (GASOLINE, DIESEL, ELECTRIC, HYBRID):");
                        String engineInput = sc.next().toUpperCase();
                        for (MotorEnum m : MotorEnum.values()) {
                            if (m.name().equals(engineInput)) {
                                engineTruk = m;
                                validEngine = true;
                                break;
                            }
                        }
                        if (!validEngine) {
                            System.out.println("Invalid engine type. Please try again.");
                        }
                    }

                    validDate = false;
                    fabricationDate = "";
                    while (!validDate) {
                        System.out.println("Enter the fabrication date (DD/MM/YYYY):");
                        fabricationDate = sc.next();
                        if (fabricationDate.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                            LocalDate date = LocalDate.parse(fabricationDate, formatter);
                            fabricationDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            validDate = true;
                        } else {
                            System.out.println("Incorrect date format. Use DD/MM/YYYY.");
                        }
                    }

                    System.out.println("Enter the truk weight (kg):");
                    weight = sc.nextDouble();

                    Truk newTruk = new Truk(brand, plate, model, power, seats, colorTruk, engineTruk, fabricationDate, weight);
                    listaVehicle.add(newTruk);
                    System.out.println("Truk created successfully.");
                    break;

                case "4":
                    if (listaVehicle.isEmpty()) {
                        System.out.println("The vehicle list is empty.");
                    } else {
                        System.out.println("Vehicle list:");
                        for (Vehicle vehicle : listaVehicle) {
                            System.out.println(vehicle);
                        }
                    }
                    break;

                case "5":
                    System.out.println("Enter the plate of the vehicle you want to view: ");
                    selectionPlate = sc.next();

                    boolean found = false;
                    for (Vehicle vehicle : listaVehicle) {
                        if (vehicle.getPlate().equalsIgnoreCase(selectionPlate)) {
                            System.out.println(vehicle);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("No vehicle found with that plate.");
                    }
                    break;

                case "6":
                    System.out.println("Total vehicles created: " + listaVehicle.size());
                    break;

                case "7":
                    long totalCars = listaVehicle.stream().filter(v -> v instanceof Car).count();
                    System.out.println("Total cars created: " + totalCars);
                    break;

                case "8":
                    long totalMotorcycles = listaVehicle.stream().filter(v -> v instanceof Motorcicle).count();
                    System.out.println("Total motorcycles created: " + totalMotorcycles);
                    break;

                case "9":
                    long totalTruks = listaVehicle.stream().filter(v -> v instanceof Truk).count();
                    System.out.println("Total truks created: " + totalTruks);
                    break;

                case "10":
                    System.out.println("Enter the plate of the vehicle you want to delete: ");
                    selectionPlate = sc.next();
                    boolean deleted = false;

                    for (Vehicle vehicle : listaVehicle) {
                        if (vehicle.getPlate().equalsIgnoreCase(selectionPlate)) {
                            listaVehicle.remove(vehicle);
                            System.out.println("Vehicle with plate " + selectionPlate + " has been deleted.");
                            deleted = true;
                            break;
                        }
                    }

                    if (!deleted) {
                        System.out.println("No vehicle found with that plate.");
                    }
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        } while (true);
    }
}
