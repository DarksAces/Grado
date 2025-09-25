/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Ejercicios;

import java.util.Scanner;

/**
 *
 * @author danielgarbru
 */
public class Ejercicio1 {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Introduce un numero: ");
        double x=sc.nextDouble();

        System.out.println("Introduce un segundo numero: ");
        double y=sc.nextDouble();
        System.out.println("La suma de " + x + " + " + y + " es igual a: " + (x + y));
        System.out.println("La resta de " + x + " - " + y + " es igual a: " + (x - y));
        System.out.println("La multiplicacion de " + x + " X " + y + " es igual a: " + (x * y));
        System.out.println("La multiplicacion de " + x + " / " + y + " es igual a: " + (x / y));

    }

}
