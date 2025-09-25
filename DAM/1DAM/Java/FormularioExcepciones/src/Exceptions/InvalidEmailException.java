package Exceptions;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danielgarbru
 */
public class InvalidEmailException extends Exception {
        
        public InvalidEmailException() {
            super("Email inválido");
        }
        
        public InvalidEmailException(String message) {
            super(message);
        }
    }