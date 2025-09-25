/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejemplos.examen;
import java.util.Scanner;

/**
 *
 * @author danielgarbru
 */
public class Ejemploexamen {
static Scanner sc = new Scanner(System.in);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int dias = 0;
        int agua = 0;
        int total = 0;
        int limite = 0;
        int diaspasados = 1;
        
        System.out.println("Introduce el limite de litros que han establecido");
        limite = sc.nextInt();
        System.out.println("Introduce la cantidad de dias que has de pasar con ese limite");
        dias = sc.nextInt();
        for (int i = 0; i < dias; i++) {
            System.out.println("Introduce los litros consumidos en el dia " + diaspasados);
            agua = sc.nextInt();
            total += agua;
            diaspasados ++;
        }
        if (total < limite ) {
            System.out.println("Muy bien has ahorrado " + (limite - total) + " Litros" );
        }
        else if (total == limite){
            System.out.println("Has quedado hay al limite pero no has pasado muy bien");
            
        }
        else if (total > limite) {
            System.out.println("Muy mal has pasado el limite de litros por " + (total - limite) + " litros");
            
        }
        
    }
    
}
