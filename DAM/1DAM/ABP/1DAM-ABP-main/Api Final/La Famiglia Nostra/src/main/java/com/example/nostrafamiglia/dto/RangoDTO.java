package com.example.nostrafamiglia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RangoDTO {
    private int idRango; //*
    private String rango;
}
// si quiero quitar el campo del id en el dto solo tengo que borrarlo aquí y en el service
