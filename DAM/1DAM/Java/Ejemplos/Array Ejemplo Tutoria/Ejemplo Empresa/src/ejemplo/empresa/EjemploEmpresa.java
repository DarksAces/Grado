/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejemplo.empresa;

/**
 *
 * @author danielgarbru
 */
public class EjemploEmpresa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int ej1[] = {1, 2, 3, 4};
        int ej2[] = {1, 2, 4, 4};

        for (int sol : ej1) {
            System.out.println(sol);
            for (int i = 0; i < ej1.length; i++) {
                if (ej1[i] + ej1[0] == 8) {
                    System.out.println("xd");
                }
                else if (ej1[i] + ej1[0] != 8) {
                    System.out.println("a");
                    i++;
                }
                System.out.println("fin");
            }

        }

    }
}
