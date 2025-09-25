/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica09;

import java.util.Scanner;

/**
 *
 * @author danielgarbru
 */
public class Practica09 {

    static Scanner sc = new Scanner(System.in);
    static final int MAXEJ1 = 10;
    static final int MAXEJ2 = 20;
    static final int MAXEJ3 = 10;
    static final int MAXEJ5 = 100;
    static final int MAXEJ6 = 8;
    static final int MAXEJ7 = 10;

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
            System.out.println("8 - Exercise 8 ");
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
//                case "8":
//                    Exercise08();
//                    break;
                case "Z":
                    System.out.println("Adios");
                    break;
                default:
                    System.out.println("Opcion Incorrecta");
            }

        } while (!opcion.equals("Z"));
    }

    static void Exercise01() {

        int[] lista10 = new int[MAXEJ1];
        System.out.println("Introduce un numero: ");
        for (int i = 0; i < MAXEJ1; i++) {

            lista10[i] = sc.nextInt();
            System.out.println("Introduce otro numero ");
        }

        System.out.println("Aqui tienes la lista en orden inverso: ");
        for (int i = MAXEJ1 - 1; i >= 0; i--) {
            System.out.println(lista10[i]);
        }
    }

    static void Exercise02() {
        int[] listaNumbs = new int[MAXEJ2];
        int[] listaSquare = new int[MAXEJ2];
        int[] listaCube = new int[MAXEJ2];

        for (int i = 0; i < listaNumbs.length; i++) {
            listaNumbs[i] = (int) (Math.random() * 101);
            listaSquare[i] = listaNumbs[i] * listaNumbs[i];
            listaCube[i] = listaNumbs[i] * listaNumbs[i] * listaNumbs[i];
        }
        System.out.printf("%-10s %-10s %-10s%n", "Numero", "Cuadrado", "Cubo");
        for (int i = 0; i < listaNumbs.length; i++) {
            System.out.printf("%-10d %-10d %-10d%n", listaNumbs[i], listaSquare[i], listaCube[i]);
        }
    }

    static void Exercise03() {
        int[] listaMaxMin = new int[MAXEJ3];
        int numero = 1;

        for (int i = 0; i < listaMaxMin.length; i++) {
            System.out.println("Introduce el numero " + numero + ":");
            listaMaxMin[i] = sc.nextInt();
            numero++;
        }

        int max = listaMaxMin[0];
        int min = listaMaxMin[0];

        for (int i = 1; i < listaMaxMin.length; i++) {
            if (listaMaxMin[i] > max) {
                max = listaMaxMin[i];
            }
            if (listaMaxMin[i] < min) {
                min = listaMaxMin[i];
            }
        }

        System.out.println("Numeros ingresados:");
        for (int i = 0; i < listaMaxMin.length; i++) {
            if (listaMaxMin[i] == max) {
                System.out.println(listaMaxMin[i] + " (maximo)");
            } else if (listaMaxMin[i] == min) {
                System.out.println(listaMaxMin[i] + " (minimo)");
            } else {
                System.out.println(listaMaxMin[i]);
            }
        }
    }

    public static void Exercise04() {
        String[] palabra = new String[15];

        System.out.println("Escribe 15 palabras distintas: ");
        for (int i = 0; i < palabra.length; i++) {
            palabra[i] = sc.next();
        }

        String ultimaPalabra = palabra[palabra.length - 1];

        for (int i = palabra.length - 1; i > 0; i--) {
            palabra[i] = palabra[i - 1];
        }

        palabra[0] = ultimaPalabra;

        System.out.println("Aqui esta la lista modificada");
        for (String word : palabra) {
            System.out.println(word);
        }

    }

    public static void Exercise05() {
        int[] listaNums = new int[MAXEJ5];

        for (int i = 0; i < listaNums.length; i++) {
            listaNums[i] = (int) (Math.random() * 21);
        }

        System.out.println("Numeros generados:");
        for (int nums : listaNums) {
            System.out.print(nums + " ");
        }
        System.out.println();

        System.out.println("Introduce el primer valor:");
        int valor1 = sc.nextInt();
        System.out.println("Introduce el segundo valor:");
        int valor2 = sc.nextInt();

        System.out.println("Lista despues del cambio:");
        for (int i = 0; i < listaNums.length; i++) {
            if (listaNums[i] == valor1) {
                System.out.print("\"" + valor2 + "\" ");
            } else {
                System.out.print(listaNums[i] + " ");
            }
        }
    }

    public static void Exercise06() {
        int[] listaNum = new int[MAXEJ6];
  
        for (int i = 0; i < MAXEJ6; i++) {
            System.out.println("Introduce un numero entero: ");
            listaNum[i] = sc.nextInt();
        }

        System.out.println("Aqui tienes cuales son los numeros pares e impares ");
        for (int i : listaNum) {
            if (i % 2 == 0) {
                System.out.println(i + " Es par");
            } else {
                System.out.println(i + " Es impar");
            }
        }

    }

    public static boolean esPrimo(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void Exercise07() {
        int[] listaNum = new int[MAXEJ7];
        int[] listaPrimos = new int[MAXEJ7];
        int[] listaNoPrimos = new int[MAXEJ7];
        int primoIndex = 0, noPrimoIndex = 0;

        // Leer números del teclado
        System.out.println("Introduce 10 numeros enteros: ");
        for (int i = 0; i < MAXEJ7; i++) {
            listaNum[i] = sc.nextInt();
            System.out.println("Introduce otro numero: ");
        }

        // Mostrar contenido de listaNum con sus índices
        System.out.printf("%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%n", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        System.out.println();
        for (int num : listaNum) {
            System.out.printf("%-10d", num);
        }
        System.out.println();

        // Separar números primos y no primos
        for (int num : listaNum) {
            if (esPrimo(num)) {
                listaPrimos[primoIndex++] = num;
            } else {
                listaNoPrimos[noPrimoIndex++] = num;
            }
        }

        // Combinar primos y no primos en listaNum
        for (int i = 0; i < primoIndex; i++) {
            listaNum[i] = listaPrimos[i];
        }
        for (int i = 0; i < noPrimoIndex; i++) {
            listaNum[primoIndex + i] = listaNoPrimos[i];
        }

        // Mostrar contenido de listaNum después de ordenar
        System.out.println("\nNumeros después de mover primos al inicio:");
        System.out.printf("%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%n", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        System.out.println();
        for (int num : listaNum) {
            System.out.printf("%-10d", num);
        }
        System.out.println();
    }

    public static void Exercise08() {

    }
}