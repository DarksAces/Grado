package practica06;

import java.util.Scanner;

/**
 *
 * @author danielgarbru
 */
public class Practica06 {

    /**
     * @param args the command line arguments
     */
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        sc.useDelimiter("\n");
        System.out.println("");
        System.out.println("Menu: ");
        System.out.println("1 - Exercise 1 ");
        System.out.println("2 - Exercise 2 ");
        System.out.println("3 - Exercise 3 ");
        System.out.println("4 - Exercise 4 ");
        System.out.println("5 - Exercise 5 ");
        System.out.println("6 - Exercise 6 ");
        System.out.println("Enter option:");
        String opcion = sc.next();

        switch (opcion) {
            case "1":
                Exercise1();
                break;
            case "2":
                Exercise2();
                break;
            case "3":
                Exercise3();
                break;
            case "4":
                Exercise4();
                break;
            case "5":
                Exercise5();
                break;
            case "6":
                Exercise6();
                break;

            default:
                System.out.println("Opcion Incorrecta");
        }

    }

        static void Exercise1() {
            int score = 0;
            System.out.println("Question 1: What is the output of 'System.out.println(5 + 3);'?");
            System.out.println("a) 53");
            System.out.println("b) 8");
            System.out.println("c) 5+3");
            System.out.println("d) Error");
            System.out.println("Answer:");
            String Question1 = sc.next();
            System.out.println("");
            if (Question1.equals("B")) {
                score++;
            }

            System.out.println("Question 2: Wich keyword is used to create a class in Java?");
            System.out.println("a) class");
            System.out.println("b) create");
            System.out.println("c) new");
            System.out.println("d) method");
            System.out.println("Answer:");
            String Question2 = sc.next();
            System.out.println("");
            if (Question2.equals("A")) {
                score++;
            }

            System.out.println("Question 3: What data type is used to create a variable that should store");
            System.out.println("a) class");
            System.out.println("b) create");
            System.out.println("c) new");
            System.out.println("d) method");
            System.out.println("Answer:");
            String Question3 = sc.next();
            System.out.println("");
            if (Question3.equals("B")) {
                score++;
            }

            System.out.println("Question 4: What data type is used to create a variable that should store");
            System.out.println("a) public void main()");
            System.out.println("b) public static void main()");
            System.out.println("c) main()");
            System.out.println("d) method");
            System.out.println("Answer:");
            String Question4 = sc.next();
            System.out.println("");
            if (Question4.equals("B")) {
                score++;
            }

            System.out.println("Question 5: What is the correct way to create a variable of type int?");
            System.out.println("a) int num = 5");
            System.out.println("b) integer num = 5");
            System.out.println("c) num int = 5");
            System.out.println("d) num = int 5");
            System.out.println("Answer:");
            String Question5 = sc.next();
            System.out.println("");
            if (Question5.equals("A")) {
                score++;
            }

            System.out.println("Final Grade: " + score + "/5");
        }

        static void Exercise2() {
            System.out.println("Enter a 3-digit number: ");
            int number = sc.nextInt();
            int numero2 = number / 100;
            int numero3 = number % 10;
            String num2 = numero2 + "";
            String num3 = numero3 + "";

            if (((number >= 100) && (number <= 999)) || ((number <= -100) && (number >= -999)) && (num2.equals(num3))) {
                System.out.println("The number is capicua");
            } else {
                System.out.println("The number is not capicua");
            }
        }

        static void Exercise3() {
            System.out.println("Enter a 4-digit number: ");
            int number = sc.nextInt();
            if (((number >= 0) && (number <= 9)) || ((number <= -1) && (number >= -9))) {
                System.out.println("The number has 1 digits");
            } else if (((number >= 10) && (number <= 99)) || ((number <= -10) && (number >= -99))) {
                System.out.println("The number has 2 digits");
            } else if (((number >= 100) && (number <= 999)) || ((number <= -100) && (number >= -999))) {
                System.out.println("The number has 3 digits");
            } else if (((number >= 1000) && (number <= 9999)) || ((number <= -1000) && (number >= -9999))) {
                System.out.println("The number has 4 digits");
            } else if (((number >= 10000) && (number <= 99999)) || ((number <= -10000) && (number >= -99999))) {
                System.out.println("The number has 5 digits");
            } else {
                System.out.println("Opcion incorrecta");
            }

        }

        static void Exercise4() {

            final int MINUTOS_SEMANA = 6660;
            final int MIN_DIA_ENTERO = 1440;
            final int VIERNES = 900;

            Scanner sc = new Scanner(System.in);

            System.out.print("Enter a day of the week: ");
            String dia = sc.next();
            System.out.println("Enter time (HH MM) : ");
            int hora = sc.nextInt();
            int min = sc.nextInt();

            int minutosTotales = (hora * 60) + min;
            int minutosRestantes = 0;

            switch (dia.toLowerCase()) {
                case "monday":
                    minutosRestantes = MINUTOS_SEMANA - minutosTotales;
                    break;
                case "tuesday":
                    minutosRestantes = MINUTOS_SEMANA - minutosTotales - MIN_DIA_ENTERO;
                    break;
                case "wednesday":
                    minutosRestantes = MINUTOS_SEMANA - minutosTotales - 2 * MIN_DIA_ENTERO;
                    break;
                case "thursday":
                    minutosRestantes = MINUTOS_SEMANA - minutosTotales - 3 * MIN_DIA_ENTERO;
                    break;
                case "friday":
                    minutosRestantes = VIERNES - minutosTotales;
                    break;
                default:
                    System.out.println("Invalid day entered.");
                    return;
            }

            System.out.println("Minutes until weekend: " + minutosRestantes);

        }

        static void Exercise5() {
            final double GENERAL = 0.21;
            final double REDUCED = 0.10;
            final double SUPERREDUCED = 0.04;
            final double NOPROMO = 1.0;
            final double HALF = 2.0;
            final double FIXDISCOUNT = 5.0;

            System.out.print("Enter the taxable base: ");
            double base = sc.nextDouble();
            System.out.print("Enter the TAX type (general, reduced, super-reduced): ");
            String tax = sc.next();
            System.out.print("Enter the promo code (noPromo, half, fixDiscount, percentage): ");
            String promos = sc.next();

            double taxRate = 0;
            if (tax.equals("general")) {
                taxRate = GENERAL;
            } else if (tax.equals("reduced")) {
                taxRate = REDUCED;
            } else if (tax.equals("super-reduced")) {
                taxRate = SUPERREDUCED;
            } else {
                System.out.println("Invalid tax type");
                return;
            }

            double PrecioImpuestos = base * (1 + taxRate);
            double descuento = 0;
            if (promos.equals("noPromo")) {
                descuento = 0;
            } else if (promos.equals("half")) {
                descuento = PrecioImpuestos / HALF;
            } else if (promos.equals("fixDiscount")) {
                descuento = FIXDISCOUNT;
            } else if (promos.equals("percentage")) {
                descuento = PrecioImpuestos * 0.05;
            } else {
                System.out.println("Invalid promo code");
                return;
            }

            double total = PrecioImpuestos - descuento;

            System.out.printf("%-20s  %10.2f%n", "Taxable base:", base);
            System.out.printf("%-20s  %10.2f%n", "TAX" + "(" + (taxRate * 100) + "%)", base - taxRate * 100);
            System.out.printf("%-20s  %10.2f%n", "Price with taxes:", PrecioImpuestos);
            System.out.printf("%-20s %10s%n", "Promo code:", promos);
            System.out.printf("%-20s %10.2f%n", "Discount:", -descuento);
            System.out.printf("%-20s  %10.2f%n", "TOTAL:", total);
        }

        static void Exercise6() {

            boolean jugada1 = true;
            boolean jugada2 = true;
            System.out.print("Turno del Jugador 1 (Introdzca piedra papel o tijera): ");
            String opcionjugador1 = sc.next();
            switch (opcionjugador1) {
                case "papel":
                    jugada1 = true;
                    break;
                case "piedra":
                    jugada1 = true;
                    break;
                case "tijeras":
                    jugada1 = true;
                    break;
                default:
                    jugada1 = false;
                    System.out.println("Jugada no valida");
                    break;

            }
            System.out.print("Turno del Jugador 2 (Introdzca piedra papel o tijera): ");
            String opcionjugador2 = sc.next();
            switch (opcionjugador2) {
                case "papel":
                    jugada2 = true;
                    break;
                case "piedra":
                    jugada2 = true;
                    break;
                case "tijeras":
                    jugada2 = true;
                    break;
                default:
                    jugada2 = false;
                    System.out.println("Jugada no valida");
                    break;
            }
            switch (opcionjugador2) {
                case "papel":
                    jugada2 = true;
                    break;
                case "piedra":
                    jugada2 = true;
                    break;
                case "tijeras":
                    jugada2 = true;
                    break;
                default:
                    jugada2 = false;
                    System.out.println("Jugada no valida");
                    break;
            }
            if (jugada2 && jugada1) {
                switch (opcionjugador1) {
                    case "papel":
                        if (opcionjugador2.equals("piedra")) {
                            System.out.println("Gana el jugador 1");
                        } else if (opcionjugador2.equals("tijera")) {
                            System.out.println("Gana el jugador 2");
                        } else {
                            System.out.println("Empate");
                        }
                        break;
                    case "piedra":
                        if (opcionjugador2.equals("tijera")) {
                            System.out.println("Gana el jugador 1");
                        } else if (opcionjugador2.equals("papel")) {
                            System.out.println("Gana el jugador 2");
                        } else {
                            System.out.println("Empate");
                        }
                        break;
                    case "tijera":
                        if (opcionjugador2.equals("papel")) {
                            System.out.println("Gana el jugador 1");
                        } else if (opcionjugador2.equals("piedra")) {
                            System.out.println("Gana el jugador 2");
                        } else {
                            System.out.println("Empate");
                        }
                        break;
                    default:
                        System.out.println("Empate");
                        break;
                }
            }
        }
}
