package com.example.demo_DGB.model;
import jakarta.persistence.*;
import java.util.List;
@Entity
@Table(name = "categorias")
public class category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    // Relación bidireccional con Producto
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<product> products;
    // Constructores, Getters, Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<product> getProducts() {
        return products;
    }

    public void setProducts(List<product> products) {
        this.products = products;
    }
}

