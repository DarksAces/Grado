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
@Table(name = "armas")
public class Arma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // *
    @Column(name = "id_arma")
    private int idArma;

    @Column(name = "nombre_arma", nullable = false)
    private String nombreArma;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "calibre")
    private Double calibre;

    @Column(name = "municion")
    private String municion;

    @Column(name = "precio")
    private BigDecimal precio;

}
// @GeneratedValue(...): le dice a JPA que el valor se genera automáticamente.
//strategy = GenerationType.SEQUENCE: se usará una secuencia de Oracle.
//generator = "armas_seq": nombre interno en JPA para identificar la secuencia.
//
//@SequenceGenerator(...): define qué secuencia de Oracle se usará.
//sequenceName = "armas_seq" → el nombre real de tu secuencia en Oracle.
//allocationSize = 1 → indica que se incrementará de uno en uno.