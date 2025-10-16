package com.example.demo_DGB.controller;

import com.example.demo_DGB.Service.CategoryService;
import com.example.demo_DGB.dto.productCreateDTO;
import com.example.demo_DGB.dto.productDTO;
import com.example.demo_DGB.model.category;
import com.example.demo_DGB.model.product;
import com.example.demo_DGB.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    // Crear producto usando DTO de entrada (ProductoCreateDTO)
    @PostMapping
    public ResponseEntity<productDTO> createProduct(@RequestBody productCreateDTO dto) {
        // Convertir DTO -> Entidad
        product p = new product();
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        p.setStock(dto.getStock());
        p.setManufacturingCost(dto.getManufacturingCost());

        // Asignar categoría si se indica
        if (dto.getCategoryId() != null) {
            category c = categoryService.searchCategory(dto.getCategoryId());
            p.setCategoria(c);
        }

        // Guardar producto
        product guardado = productService.guardarProducto(p);

        // Convertir Entidad -> DTO
        productDTO respuesta = convertirAProductoDTO(guardado);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    // Listar productos (mostrando DTO sin stock ni costeFabricacion)
    @GetMapping
    public ResponseEntity<List<productDTO>> listProducts() {
        List<product> products = productService.listProducts();
        List<productDTO> listDTO = new ArrayList<>();
        for (product p : products) {
            listDTO.add(convertirAProductoDTO(p));
        }
        return ResponseEntity.ok(listDTO);
    }

    // Obtener un producto por ID con DTO
    @GetMapping("/{id}")
    public ResponseEntity<productDTO> getProducto(@PathVariable Long id) {
        product p = productService.buscarProducto(id);
        if (p == null) {
            return ResponseEntity.notFound().build();
        }
        productDTO dto = convertirAProductoDTO(p);
        return ResponseEntity.ok(dto);
    }

    // Actualizar un producto usando DTO de entrada
    @PutMapping("/{id}")
    public ResponseEntity<productDTO> actualizarProducto(@PathVariable Long id, @RequestBody productCreateDTO dto) {
        // Lógica para actualizar el producto
        return null;
    }


    private productDTO convertirAProductoDTO(product p) {
        productDTO dto = new productDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setPrice(p.getPrice());

        // Asignamos el nombre de la categoría si existe
        if (p.getCategoria() != null) {
            dto.setCategoryName(p.getCategoria().getName());
        }

        return dto;
    }
}
