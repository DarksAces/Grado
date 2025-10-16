/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejemplos.ficheros;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danielgarbru
 */
public class LecturaConScanner {

    static Scanner keyboard = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        String fileName = "C:\\Users\\danielgarbru\\Desktop\\A.txt";
        String opcion = null;

        do {
            try {
                System.out.println("Menu: ");
                System.out.println("1.- Leer fichero");
                System.out.println("Z.- Salir: ");
                System.out.print("Opcion: ");
                opcion = keyboard.next().toUpperCase();
                
                switch (opcion) {
                    case "1":
                        String contentFile;
                        contentFile = leerFichero(fileName);
                        System.out.println(contentFile);
                        break;
                    case "Z":
                        System.out.println("Saliendo...");
                        System.out.println("Saliendo...");
                        System.out.println("Saliendo...");
                        System.out.println("Se marcho");
                        break;
                    default:
                        System.out.println("Opcion incorrecta");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }

        } while (!opcion.equals("Z"));

    }

    private static String leerFichero(String fileName) throws FileNotFoundException {
        String contenido = "";
        File fichero = new File(fileName);
        Scanner sc = new Scanner(fichero);
        sc.useDelimiter("\n");
        String lineas;
        while (sc.hasNext()) {
            lineas = sc.next();
            contenido += lineas + "\n";
//            System.out.println(lineas);
        }
        sc.close();
        return contenido;
    }
}
