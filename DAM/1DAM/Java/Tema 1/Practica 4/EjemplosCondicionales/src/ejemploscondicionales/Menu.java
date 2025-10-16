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
public class Menu {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        sc.useDelimiter("\n");

        System.out.println("Practica 05");
        System.out.println("Menu: ");
        System.out.println("1.- ejercicio1");
        System.out.println("2.- ejercicio2");
        System.out.println("3.- ejercicio3");
        System.out.print("Opcion: ");
        String opcion = sc.next();

        switch (opcion) {
            case "1":
                ejercicio1();
                break;
            case "2":
                ejercicio2();
                break;
            case "3":
                ejercicio3();
                break;
           
            default:
                System.out.println("Opcion Incorrecta");
        }

    }//Fin Main
    static void ejercicio1 (){
        
        String numStr = sc.next();
        int num = Integer.parseInt(numStr);
        double numDec = Double.parseDouble(numStr);
    }
    static void ejercicio2 (){
        
    }
    static void ejercicio3 (){
        
    }
}
