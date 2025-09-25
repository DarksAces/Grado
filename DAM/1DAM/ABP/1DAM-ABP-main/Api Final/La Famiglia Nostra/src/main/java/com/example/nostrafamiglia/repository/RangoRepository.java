package com.example.nostrafamiglia.repository;

import com.example.nostrafamiglia.model.Rango;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RangoRepository extends JpaRepository<Rango, Integer> {
}
