package com.example.nostrafamiglia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "servicios")
public class Servicios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // *
    @Column(name = "id_servicio")
    private int idServicio;

    @Column(name = "nombre_servicio", nullable = false)
    private String nombreServicio;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "precio_base", nullable = false)
    private BigDecimal precioServicio;

}