package com.example.nostrafamiglia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactoDTO {
    private int idContacto;
    private String firstName;
    private String lastName;
    private String apodo;
    private String numero;
    private String campoInteres;
}
