package com.example.demo_DGB.model;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@Table(name = "productos")

public class product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private Integer stock;
    private Double manufacturingCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private category categoria;

    // Getters and setters
    public category getCategoria() {
        return categoria;
    }

    public void setCategoria(category categoria) {
        this.categoria = categoria;
    }

    // Other getters and setters
}
