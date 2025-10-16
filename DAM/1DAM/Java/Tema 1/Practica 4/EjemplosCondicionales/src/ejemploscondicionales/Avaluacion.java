/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemploscondicionales;

import java.util.Scanner;

/**
 * /
 *
 *
 * @author danielgarbru
 */
public class Avaluacion {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Que nota has sacado? ");
        double nota=sc.nextDouble();

        if ((nota >= 9) && (nota <= 10)) {
            System.out.println("Tu nota es Excelente");
        } else if ((nota >= 6.5) && (nota < 9)) {
            System.out.println("Tu nota es notable");
        } else if ((nota >= 5) && (nota < 6.5)) {
            System.out.println("Has aprobado");
        } else if ((nota == 0) && (nota < 4.9)) {
            System.out.println("Has suspendido");
        } else {
            System.out.println("La nota introducida no es valida, introduce una nota valida");
        }
    }
}
