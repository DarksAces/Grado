package com.example.nostrafamiglia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArmaDTO {
    private int idArma;
    private String nombreArma;
    private String tipo;
    private BigDecimal precio;


}
// si quiero quitar el campo del id en el dto solo tengo que borrarlo aquí y en el service
