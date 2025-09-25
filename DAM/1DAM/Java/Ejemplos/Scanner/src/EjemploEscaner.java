
import java.util.Scanner;

/**
 *
 * @author danielgarbru
 */

public class EjemploEscaner {
    static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println("Introduce tu nombre: ");
        String nombre = sc.next();
        System.out.println("Introduce tu nombre: ");
        String apellido = sc.next();
        System.out.println("Hola " + nombre + " "+ apellido + "!");
        
                
    }

}
