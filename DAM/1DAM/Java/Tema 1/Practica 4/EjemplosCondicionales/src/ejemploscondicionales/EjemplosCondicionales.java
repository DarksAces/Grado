/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejemploscondicionales;
import java.util.Scanner;
/**
 *
 * @author danielgarbru
 */
public class EjemplosCondicionales {
    
    /**
     * @param args the command line arguments
     */
       static Scanner sc = new Scanner (System.in);
    public static void main(String[] args) {
        sc.useDelimiter("\n");
        
        boolean puedeTrabajar = false;
        // TODO code application logic here
            System.out.print("Edad: ");
            int edad = sc.nextInt();
            
      
            
            if ((edad >=18) && (edad <= 65)) {
             System.out.println("Puedes trabajar");
        }
            else 
                 System.out.println("No puedes trabajar");
           
    }


}
