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
public class Ejercicio9 {
static Scanner sc = new Scanner(System.in);
static final double pi = 3.14;
public static void main(String [] args){
  System.out.println("Introduce el radio");
  double radio=sc.nextDouble();
  System.out.println("Introduce la altura");
  double altura=sc.nextDouble();
  double total = (((radio*radio)* altura * pi )/3);
  System.out.println("El volumen de este cono es: " + total);
}
}
