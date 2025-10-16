package com.example.nostrafamiglia.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.nostrafamiglia.model.Arma;

@Repository
public interface ArmaRepository extends JpaRepository<Arma, Integer> {
}
//Aquí es donde hereda de la clase JpaRepository, que ya viene con muchos métodos útiles como:
//findAll(): devuelve todas las armas.
//findById(1): busca un arma por ID.
//save(rango): guarda o actualiza un arma.
//deleteById(1): elimina un arma por ID.