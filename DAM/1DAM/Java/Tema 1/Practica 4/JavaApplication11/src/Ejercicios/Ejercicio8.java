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
public class Ejercicio8 {
static Scanner sc = new Scanner(System.in);
public static void main(String [] args){
    double salario;
    salario = 12;
    System.out.println("Introduce cuantas horas a la semana trabajas: ");
    double Horas=sc.nextDouble();
    System.out.println("Por trabajar " +Horas+ " horas deberias cobrar " + (Horas * salario));
}
}
