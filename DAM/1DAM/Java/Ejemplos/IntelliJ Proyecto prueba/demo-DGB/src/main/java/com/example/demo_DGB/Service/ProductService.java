package com.example.demo_DGB.Service;
import com.example.demo_DGB.model.product;
import com.example.demo_DGB.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<product> listProducts() {
        // Devuelve todos los registros de la tabla 'producto'
        return productRepository.findAll();
    }
    public product buscarProducto(Long id) {
        // Buscar por ID
        // Retorna null si no existe (evitamos Optional en este ejemplo básico)
        product p = productRepository.findById(id).orElse(null);
        return p;
    }
    public product guardarProducto(product p) {
        // Inserta o actualiza (si p tiene un ID existente)
        return productRepository.save(p);
    }
    public boolean borrarProducto(Long id) {
        // Verifica si existe
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}


