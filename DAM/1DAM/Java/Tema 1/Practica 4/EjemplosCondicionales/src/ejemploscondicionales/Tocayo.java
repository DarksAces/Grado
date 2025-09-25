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
public class Tocayo {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args){
     sc.useDelimiter("\n");
     String miNombre = "Daniel";
     System.out.println("Cual es tu nombre? ");
     String nombre = sc.next();
     
     if (miNombre.equals(nombre)){
         System.out.println("Tocayo !");
     }
    }
    
}
