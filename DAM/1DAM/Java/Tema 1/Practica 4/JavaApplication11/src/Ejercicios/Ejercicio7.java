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
public class Ejercicio7 {
static Scanner sc = new Scanner(System.in);
public static void main(String [] args){
    System.out.println("Introduce una letra");
    char letra1 =(sc.next()).charAt(0);    
    System.out.println("Introduce una letra");
    char letra2 =(sc.next()).charAt(0);
    System.out.println("Introduce una letra");
    char letra3 =(sc.next()).charAt(0);
    System.out.println("Introduce una letra");
    char letra4 =(sc.next()).charAt(0);    
    System.out.println("Introduce una letra");
    char letra5 =(sc.next()).charAt(0);
    
    String conjunto = ("" + letra1 + letra2 + letra3 + letra4 + letra5);
    System.out.println("Los valores introducidos en cadena son: " + conjunto);
}
}
