package com.example.nostrafamiglia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiciosDTO {
    private int idServicios;
    private String nombreServicio;
    private String descripcion;
    private BigDecimal precioServicio;

}
