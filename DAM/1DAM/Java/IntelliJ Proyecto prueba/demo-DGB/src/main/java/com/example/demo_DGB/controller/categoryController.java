package com.example.demo_DGB.controller;
import com.example.demo_DGB.dto.categoryDTO;
import com.example.demo_DGB.model.category;
import com.example.demo_DGB.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class categoryController {

    @Autowired
    private CategoryService categoryService;

    // Crear una nueva categoría
    @PostMapping
    public ResponseEntity<categoryDTO> createCategory(@RequestBody categoryDTO dto) {
        // DTO -> Entidad
        category c = new category();
        c.setName(dto.getName());
        c.setDescription(dto.getDescription());

        // Guardar en la base de datos
        category saved = categoryService.saveCategory(c);

        // Verificar que se guardó correctamente
        if (saved == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Entidad -> DTO
        categoryDTO response = convertToCategoryDTO(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Listar todas las categorías
    @GetMapping
    public ResponseEntity<List<categoryDTO>> listarCategorias() {
        List<category> lista = categoryService.listCategories();
        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<categoryDTO> dtos = new ArrayList<>();
        for (category c : lista) {
            dtos.add(convertToCategoryDTO(c));
        }
        return ResponseEntity.ok(dtos);
    }

    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<categoryDTO> getCategory(@PathVariable Long id) {
        category c = categoryService.searchCategory(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToCategoryDTO(c));
    }

    // Eliminar una categoría por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        boolean borrada = categoryService.deleteCategory(id);
        if (borrada) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Conversión de entidad a DTO
    private categoryDTO convertToCategoryDTO(category c) {
        categoryDTO dto = new categoryDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setDescription(c.getDescription());
        return dto;
    }
}
