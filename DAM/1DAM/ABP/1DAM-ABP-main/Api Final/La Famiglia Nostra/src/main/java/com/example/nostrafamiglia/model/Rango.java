package com.example.nostrafamiglia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Member;
import java.util.List;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rangos")
public class Rango {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "rangos_seq", sequenceName = "rangos_seq", allocationSize = 1)
    @Column(name = "id_rango")
    private int idRango;

    @Column(name = "rango", nullable = false)
    private String rango;

    //@OneToMany(mappedBy = "rango")
    //private List<Member> members;
}
