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
public class Ejercicio3 {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Introduce tu nombre: ");
        String nombre = sc.nextLine();
        System.out.println("Introduce tu calle: ");
        String calle = sc.nextLine();
        System.out.println("Tu nombre es: " + nombre + ", Tu calle es: " + calle);

    }

}
