package practica07;

import java.util.Scanner;

public class Practica07 {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        String opcion = "";
        do {
            sc.useDelimiter("\n");

            System.out.println("");
            System.out.println("Menu: ");
            System.out.println("1 - Exercise 1 ");
            System.out.println("2 - Exercise 2 ");
            System.out.println("3 - Exercise 3 ");
            System.out.println("4 - Exercise 4 ");
            System.out.println("5 - Exercise 5 ");
            System.out.println("6 - Exercise 6 ");
            System.out.println("7 - Exercise 7 ");
            System.out.println("Z - Salir ");
            System.out.println("Enter option:");
            opcion = sc.next().toUpperCase();

            switch (opcion) {
                case "1":
                    Exercise01();
                    break;
                case "2":
                    Exercise02();
                    break;
                case "3":
                    Exercise03();
                    break;
                case "4":
                    Exercise04();
                    break;
                case "5":
                    Exercise05();
                    break;
                case "6":
                    Exercise06();
                    break;
                case "7":
                    Exercise07();
                    break;
                case "Z":
                    System.out.println("Adios");
                    break;
                default:
                    System.out.println("Opcion Incorrecta");
            }

        } while (!opcion.equals("Z"));
    }

    static void Exercise01() {
        System.out.println("Escribe un numero para ver todos sus multiplos hasta 100: ");
        int numero = sc.nextInt();

        String OpcionBucles = "";
        do {
            if (numero > 100 || numero < 0) {
                System.out.println("opcion incorrecta");
                break;
            } else {

                sc.useDelimiter("\n");

                System.out.println("");
                System.out.println("How do you want the multiples to come out?");
                System.out.println("[1] Using for ");
                System.out.println("[2] Using while ");
                System.out.println("[3] Using do-while ");
                System.out.println("Z - Salir ");
                System.out.println("Enter option:");
                OpcionBucles = sc.next().toUpperCase();

                switch (OpcionBucles) {
                    case "1":
                        for (int i = 0; i * numero <= 100; i++) {
                            System.out.println("Este numero es multiplo: " + i * numero);
                        }
                        break;

                    case "2":
                        int i = 0;
                        while (i <= 100) {
                            if (i % numero == 0) {
                                System.out.println("Este numero es multiplo: " + i);
                            }
                            i++;
                        }

                        break;
                    case "3":
                        double j = 0;
                        do {
                            if (j % numero == 0) {
                                System.out.println("Este numero es multiplo: " + j);
                            }
                            j++;
                        } while (j <= 100);
                        break;
                    default:

                }
            }

        } while (!OpcionBucles.equals("Z"));

    }

    static void Exercise02() {

        System.out.println("Escribe un numero integral > = 10: ");
        int numeroint = sc.nextInt();
        String OpcionBucles = "";
        do {
            if (numeroint < 10) {
                System.out.println("opcion incorrecta");
                break;
            } else {

                sc.useDelimiter("\n");

                System.out.println("");
                System.out.println("How do you want the multiples to come out?");
                System.out.println("[1] Using for ");
                System.out.println("[2] Using while ");
                System.out.println("[3] Using do-while ");
                System.out.println("Z - Salir ");
                System.out.println("Enter option:");
                OpcionBucles = sc.next().toUpperCase();

            }
            switch (OpcionBucles) {

                case "1":
                    System.out.println("Contemos los numeros hasta 0 desde " + numeroint);
                    for (int i = numeroint; i >= 0; i -= 10) {
                        if (i >= 10) {
                            System.out.println(i);
                            i -= 10;
                            System.out.println(i);
                        } else if (i < 10) {
                            System.out.println(i);
                        }
                    
                    }
                    break;

                case "2":
                    System.out.println("Contemos los numeros hasta 0 desde " + numeroint);
                    int i = numeroint;
                    while (i >= 0) {
                        if (i >= 10) {
                            System.out.println(i);
                            i -= 10;
                            System.out.println(i);
                        } else if (i < 10) {
                            System.out.println(i);
                        }
     
                    }

                    break;
                case "3":

                    System.out.println("Contemos los numeros hasta 0 desde " + numeroint);
                    int j = numeroint;
                    do {
                        if (j >= 10) {
                            System.out.println(j);
                            j -= 10;
                            System.out.println(j);
                        } else if (j < 10) {
                            System.out.println(j);
                        }
      
                    } while (j >= 0);
                    break;

                default:

            }
        } while (!OpcionBucles.equals("Z"));

    }

    static void Exercise03() {

        System.out.println("Escribe un numero entero");
        int numeroint = sc.nextInt();

        System.out.println("Aqui tienes la tabla de multiplicar de " + numeroint + ":");
        for (int i = 1; i <= 10; i++) {
            System.out.println(numeroint + " x " + i + " = " + (numeroint * i));
        }
    }

    static void Exercise04() {
        System.out.print("Enter a number: ");
        int number = sc.nextInt();
        System.out.printf("%-10s %-10s %-10s%n", "Base", "Square", "Cube");
        for (int i = number; i < number + 5; i++) {
            int square = i * i;
            int cube = i * i * i;
            System.out.printf("%-10d %-10d %-10d%n", i, square, cube);
        }

    }

    static void Exercise05() {
        System.out.print("Enter a number: ");

        int number = sc.nextInt();
        int digitos = 0;
        while (number != 0) {
            digitos = digitos + 1;
            number = number / 10;

        }
        System.out.println(digitos);
    }

    static void Exercise06() {
        int Intentos = 0;
        int number = 0;
        System.out.println(" A: Intentos infinitos");
        System.out.println(" B: Maximo tres intentos");
        String opcion = sc.next().toUpperCase();
        switch (opcion) {
            case "A":
                int randomNumber = (int) (Math.random() * 10);
                System.out.println(randomNumber);
                while (number != randomNumber) {
                    System.out.println("Introduce un numero entero entre 0 y 10");
                    number = sc.nextInt();

                    System.out.println("No es ese numero reintentalo");
                    Intentos = Intentos + 1;

                }
                System.out.println("Muy bien lo adivinaste en " + Intentos + " intentos");

            case "B":
                randomNumber = (int) (Math.random() * 10);
                System.out.println(randomNumber);

                while (number != randomNumber && Intentos < 3) {
                    System.out.println("Introduce un numero entero entre 0 y 10:");
                    number = sc.nextInt();
                    Intentos++;

                    if (number != randomNumber) {
                        System.out.println("No es ese numero, reintentalo.");
                    }
                }

                if (number == randomNumber) {
                    System.out.println("¡Muy bien, lo adivinaste en " + Intentos + " intentos!");
                } else {
                    System.out.println("Lo siento, no adivinaste el numero. Era " + randomNumber);
                }
        }

    }

    static void Exercise07() {
        System.out.print("Enter base: ");
        double base = sc.nextDouble();

        System.out.print("Enter exponent: ");
        int exponent = sc.nextInt();

        double result = 1;

        if (exponent == 0) {
            result = 1;
        } else if (exponent > 0) {
            int i = 1;
            while (i <= exponent) {
                result *= base;
                i++;
            }
        } else {
            int i = 1;
            while (i <= -exponent) {
                result *= base;
                i++;
            }
            result = 1 / result;
        }

        System.out.println(base + "^" + exponent + " = " + result);
    }
}
