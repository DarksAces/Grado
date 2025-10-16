package ejemplos.bucles;

import java.util.Scanner;





/**
 *
 * @author danielgarbru
 */

public class EjemplosBucles {
    
    static Scanner sc = new Scanner(System.in);
public static void main(String[] args) {
    
    sc.useDelimiter("\n");
        System.out.println("");
        System.out.println("Menu: ");
        System.out.println("1 - Exercise 1 ");
        System.out.println("2 - Exercise 2 ");
        System.out.println("Enter option:");
        String opcion = sc.next();

        switch (opcion) {
            case "1":
                Exercise1();
                break;
            case "2":
                Exercise2();
                break;
                 default:
                System.out.println("Opcion Incorrecta");
        }

}

    /**
     * @param args the command line arguments
     */
    static void Exercise1() {

        sc.useDelimiter("\n");
        int sumaNotas = 0;
        int numAlumno = 0;

        System.out.println("Escribe la nota del alumno " + (numAlumno + 1));
        String Nota = sc.next();
        int notaInt = Integer.parseInt(Nota);
        sumaNotas = sumaNotas + notaInt;
        numAlumno = numAlumno + 1;
        System.out.println("La nota del alumno " + numAlumno + " es: " + notaInt);

        System.out.println("Escribe la nota del alumno " + (numAlumno + 1));
        Nota = sc.next();
        notaInt = Integer.parseInt(Nota);
        sumaNotas = sumaNotas + notaInt;
        numAlumno = numAlumno + 1;
        System.out.println("La nota del alumno " + numAlumno + " es: " + notaInt);

        System.out.println("Escribe la nota del alumno " + (numAlumno + 1));
        Nota = sc.next();
        notaInt = Integer.parseInt(Nota);
        sumaNotas = sumaNotas + notaInt;
        numAlumno = numAlumno + 1;
        System.out.println("La nota del alumno " + numAlumno + " es: " + notaInt);

        System.out.println("Escribe la nota del alumno " + (numAlumno + 1));
        Nota = sc.next();
        notaInt = Integer.parseInt(Nota);
        sumaNotas = sumaNotas + notaInt;
        numAlumno = numAlumno + 1;
        System.out.println("La nota del alumno " + numAlumno + " es: " + notaInt);

        System.out.println("Escribe la nota del alumno " + (numAlumno + 1));
        Nota = sc.next();
        notaInt = Integer.parseInt(Nota);
        sumaNotas = sumaNotas + notaInt;
        numAlumno = numAlumno + 1;
        System.out.println("La nota del alumno " + numAlumno + " es: " + notaInt);

        System.out.println("Nota media = " + (sumaNotas / numAlumno));
    }
static void Exercise2() {
sc.useDelimiter("\n");
        int sumaNotas = 0;
        int numAlumno = 0;
        int maximo = 5;
        int Nota = 0;
        
        while (numAlumno<= maximo){
            System.out.println("Introduce la nota del alumno: ");
       
        }

}
}

