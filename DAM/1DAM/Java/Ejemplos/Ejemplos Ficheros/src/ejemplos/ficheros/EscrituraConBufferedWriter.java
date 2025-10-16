/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplos.ficheros;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danielgarbru
 */
public class EscrituraConBufferedWriter {

    public static void main(String[] args) {
        FileWriter fw = null;
         BufferedWriter bw = null;
        
        try {
            File fichero = new File("src\\recursos\\dgb.txt");
            fw = new FileWriter(fichero);
            bw = new BufferedWriter(fw);
            bw.write("Primera \n");
            bw.write("Segunda");
            bw.newLine();
            bw.write("Tercera");
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            System.out.println("Error entrada-salida " + ex.getMessage());
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                System.out.println("Error al cerrar el fichero");
            }
        }
    }

}
