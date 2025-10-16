/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicio.pkg1;

/**
 *
 * @author danielgarbru
 */
        



public class Ejercicio5 {
    static final double iva = 0.21 ;
    public static void main(String [] args) {
        double precioiva;
        precioiva = 10;
        
        double precionoiva;
        precionoiva = precioiva-(precioiva * iva);  
        System.out.println("El precio sin iva es: " + precionoiva);
    }
}
