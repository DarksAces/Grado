/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicios;

import java.util.Scanner;

/**
 *
 * @author danielgarbru
 */
public class Ejercicio5 {
    static Scanner sc = new Scanner(System.in);
    static final double iva = 1.21 ;
    public static void main(String [] args) {
    System.out.println("Introduce una cantidad de dinero: ");
    double precionoiva=sc.nextDouble();
        double precioiva;     
        precioiva = precionoiva * iva;  
        System.out.println("El precio con iva es: " + precioiva);
    }
}
