package com.example.demo_DGB.repository;
import com.example.demo_DGB.model.category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<category,
        Long> {
}

