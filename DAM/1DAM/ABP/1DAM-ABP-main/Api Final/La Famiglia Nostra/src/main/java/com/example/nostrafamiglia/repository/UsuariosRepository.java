package com.example.nostrafamiglia.repository;

import com.example.nostrafamiglia.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository <Usuario, Integer> {
}
