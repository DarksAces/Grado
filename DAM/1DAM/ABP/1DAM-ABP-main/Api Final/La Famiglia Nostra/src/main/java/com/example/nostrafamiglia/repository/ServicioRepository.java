package com.example.nostrafamiglia.repository;

import com.example.nostrafamiglia.model.Servicios;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicios, Integer> {
}