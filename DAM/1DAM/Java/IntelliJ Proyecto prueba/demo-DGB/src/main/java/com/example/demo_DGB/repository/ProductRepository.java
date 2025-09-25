package com.example.demo_DGB.repository;

import com.example.demo_DGB.model.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductRepository extends JpaRepository<product,
        Long> {
    // Al heredar de JpaRepository, tenemos métodos como save, findById, deleteById, etc.
}

