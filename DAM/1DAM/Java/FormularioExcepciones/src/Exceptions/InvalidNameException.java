/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author danielgarbru
 */
public class InvalidNameException extends Exception {
    
    public InvalidNameException() {
        super("Nombre inválido");
    }
    
    public InvalidNameException(String message) {
        super(message);
    }
}