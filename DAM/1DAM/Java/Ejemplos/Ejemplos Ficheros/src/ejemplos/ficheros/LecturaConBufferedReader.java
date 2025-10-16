/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplos.ficheros;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danielgarbru
 */
public class LecturaConBufferedReader {
    public static void main(String[] args) {
        FileReader fr = null;
        try {
            File archivo = new File("A.txt");
            fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null){
                System.out.println(line);
                line = br.readLine();
            }
        } catch (Exception ex) {
             System.out.println("Error: " + ex.getMessage());
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                System.out.println("Error inesperado");
            }
        }
        
    }
}
