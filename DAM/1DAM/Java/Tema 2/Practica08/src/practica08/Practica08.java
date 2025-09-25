package practica08;

import java.util.Scanner;

/**
 *
 * @author danielgarbru
 */
public class Practica08 {

    static Scanner sc = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
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
        System.out.println("Escribe un numero:");
        int numero = sc.nextInt();
        int suma = 0;

        while (numero != 0) {
            suma += numero % 10;
            numero /= 10;
        }

        System.out.println("La suma de los digitos es: " + suma);
    }

    static void Exercise02() {

        String cadena = "";
        char caracter;
        int contador = 0;

        System.out.println("Ingrese un texto: ");
        cadena = sc.next().toLowerCase();

        System.out.println("Que caracter quieres saber cuantas veces esta en tu texto? ");
        caracter = sc.next().toLowerCase().charAt(0);

        for (int i = 0; i < cadena.length(); i++) {
            if (cadena.charAt(i) == caracter) {
                contador++;
            }
        }

        System.out.println("Hay un total de: " + contador + " " + caracter);
    }

    static void Exercise03() {

        String espacio = "";
        String cadena = "";
        System.out.println("Enter a word pr phrase: ");
        cadena = sc.next();

        for (int i = 0; i < cadena.length(); i++) {
            espacio = espacio + " ";
            System.out.print(espacio);
            System.out.println(cadena.charAt(i));

        }
    }

    static void Exercise04() {
        for (int filas = 1; filas <= 10; filas++) {

            for (int columnas = 1; columnas <= 10; columnas++) {
                System.out.printf("%4d", filas * columnas);
            }
            System.out.println();
        }

    }

    static void Exercise05() {

        String opcion = "";
        int heroh = 2;
        int enemyh = 2;

        while (heroh > 0 && enemyh > 0) {
            System.out.println("Quieres atacar o esquivar?");
            opcion = sc.next().toLowerCase();

            switch (opcion) {
                case "atacar":
                    enemyh--;
                    System.out.println("El enemigo pierde 1 punto de vida");
                    if (enemyh <= 0) {
                        System.out.println("Felicidades, has ganado el combate!");
                        break;
                    }
                    heroh--;
                    System.out.println("Tu enemigo ataca");
                    if (heroh <= 0) {
                        System.out.println("Has sido derrotado y la ciudad destruida");
                        break;
                    }

                    break;

                case "esquivar":
                    System.out.println("Tu enemigo intenta atacar pero lo esquivas");
                    break;

                default:
                    System.out.println("Algo ha salido mal");
                    break;
            }
        }
    }

    static void Exercise06() {
        double nota = 0;
        int numAlumno = 1;
        double sumaNotas = 0;
        int totalAlumnos = 5;

        while (numAlumno <= totalAlumnos) {
            System.out.println("Introduce la nota del alumno numero" + numAlumno);
            nota = sc.nextDouble();
            sumaNotas = sumaNotas + nota;
            numAlumno++;

        }
        double media = sumaNotas / (numAlumno - 1);
        System.out.println("La nota media es: " + media);
    }

    static void Exercise07() {
        int num = 0;
        boolean es_primo = true;
        System.out.println("Ingresa un numero entero: ");
        num = sc.nextInt();
        for (int i = 2; i < num; i++) {
            if (num % i == 0) {
                es_primo = false;
                System.out.println(i + " es divisible entre " + num);
            }

        }
        if (es_primo) {
            System.out.println("El numero " + num + " es primo");

        } else {
            System.out.println("El numero " + num + " No es primo");

        }
    }
}
