/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplos.array;

import java.util.Scanner;

/**
 *
 * @author danielgarbru
 */
public class EjemploArrayNotas {

    static Scanner sc = new Scanner(System.in);
    static final int MAX = 5;

    public static void main(String[] args) {
        int numAlumno = 1;
        double sumanotas = 0;
        double[] listanotas = new double[MAX];
        

        for (int i = 0; i < MAX; i++) {
            System.out.println("Introduce la nota del alumno " + numAlumno);
            double nota = sc.nextDouble();
            listanotas[i] = nota;
            numAlumno++;
            
        }
        for (double pepe : listanotas) {
            sumanotas = (sumanotas + pepe) ;
          
        }
        System.out.println("La media es: " + (sumanotas / 5));
    }
}
