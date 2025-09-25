/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemploscondicionales;

import java.util.Scanner;

/**
 *
 * @author danielgarbru
 */
public class Calculadora {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Calculadora");

        System.out.print("Introduce la operacion a realizar (\"+\", \"-\", \"*\", \"/\"): ");
        char op = (sc.next().charAt(0));
        System.out.println("Valor de x: ");
        double x = sc.nextDouble();

        System.out.println("Valor de y: ");
        double y = sc.nextDouble();

        switch (op) {
            case '+':
                System.out.println("La suma de " + x + "+ " + y + " Es igual a: " + (x + y));
                break;
            case '-':
                System.out.println("La resta de " + x + " - " + y + " Es igual a: " + (x - y));
                break;
            case '*':
                System.out.println("La multiplicacion de " + x + " * " + y + " Es igual a: " + (x * y));
                break;
            case '/':
                System.out.println("La division de " + x + " / " + y + " Es igual a: " + (x / y));
                break;
            default:
                System.out.println("Los datos introducidos no son validos");
                break;
        }
    }

}
