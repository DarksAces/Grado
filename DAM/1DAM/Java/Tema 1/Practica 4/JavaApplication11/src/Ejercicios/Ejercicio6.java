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
public class Ejercicio6 {
static Scanner sc = new Scanner(System.in);
public static void main(String [] args){
    System.out.println("Introduce una letra");
    char letra1 =(sc.next()).charAt(0);
    System.out.println("Introduce una palabra");
    String palabra1 = sc.next();
    System.out.println("Introduce una letra");
    char letra2 =(sc.next()).charAt(0);
    System.out.println("Introduce una palabra");
    String palabra2 = sc.next();
    
    System.out.println(" La primeta letra introducida es: " + letra1 + " La primera palabra introducida es: " + palabra1 + " La Segunda letra introducida es: " + letra2 + " La Segunda palabra introducida es: " + palabra2);
}
}
