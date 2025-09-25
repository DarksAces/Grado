package com.example.nostrafamiglia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="contactos")
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "contactos_seq", sequenceName = "contactos_seq", allocationSize = 1)
    @Column(name = "id_contacto")
    private Integer idContacto;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "apodo")
    private String apodo;

    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @Column(name = "campo_interes")
    private String campoInteres;

}
