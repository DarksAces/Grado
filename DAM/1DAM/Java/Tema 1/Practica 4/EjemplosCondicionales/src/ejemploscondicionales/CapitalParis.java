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
public class CapitalParis {
    static Scanner sc = new Scanner (System.in);
    public static void main(String[] args){
         sc.useDelimiter("\n");
         String micapital = "Paris";
         System.out.println("Escribe cual es la capital de Francia: ");
         String capital = sc.next();
         
         if (micapital.equals(capital)){
             System.out.println("La capital de Francia es París");
         }
         else
             System.out.println("La capital de Francia no es " + capital);
    }
}
