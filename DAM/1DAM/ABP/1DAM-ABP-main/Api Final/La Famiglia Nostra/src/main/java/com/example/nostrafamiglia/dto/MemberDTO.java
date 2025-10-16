package com.example.nostrafamiglia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private int idMiembro;
    private String firstName;
    private String lastName;
    private String apodo;

    // dtos con menos info
    private ArmaDTO arma;
    private RangoDTO rango;
    private JefeDTO jefe; // // es una especie de subclase para que no salga tanta mierda por pantalla
}
// si quiero quitar el campo id o otro en el dto solo tengo que borrarlo aquí y en el service