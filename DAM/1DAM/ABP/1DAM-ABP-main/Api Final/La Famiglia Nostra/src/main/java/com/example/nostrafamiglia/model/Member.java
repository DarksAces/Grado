package com.example.nostrafamiglia.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "members_seq", sequenceName = "members_seq", allocationSize = 1)
    @Column(name = "id_miembro")
    private int idMiembro;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    // Esto es la FK de la tabla de rangos
    @ManyToOne
    @JoinColumn(name = "id_rango", referencedColumnName = "id_rango")
    private Rango rango;

    // fk de las armas
    @ManyToOne
    @JoinColumn(name = "id_arma", referencedColumnName = "id_arma")
    private Arma arma;

    // fk de jefes que es un self join
    @ManyToOne
    @JoinColumn(name = "id_jefe", referencedColumnName = "id_miembro")
    @JsonIgnore // para que no muestre la parrafada infinita de este es jefe de este que es jefe de...
    private Member jefe;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "apodo")
    private String apodo;

    @Column(name = "anyo_ingreso", nullable = false)
    private LocalDate anyoIngreso;

    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

}
