package com.example.nostrafamiglia.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.nostrafamiglia.dto.ArmaDTO;
import com.example.nostrafamiglia.model.Arma;
import com.example.nostrafamiglia.service.ArmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController// indica que esta clase es un controlador REST (responde con JSON).
@RequestMapping("/lafamiglia/armas")// todas las rutas dentro de esta clase empezarán con esa URL.
@CrossOrigin(origins = "*") //esto es para que se pueda acceder desde cualquier web y se salta el CORS de seguridad ese
public class ArmaController {

    @Autowired
    private ArmaService armasService;

    @GetMapping //cuando alguien hace un GET /lafamiglia/armas.
    public ResponseEntity<List<ArmaDTO>> getAllArmas() { // lo pilla del dto
        return ResponseEntity.ok(armasService.getAllArmasDTO()); // responde OK (200) y el contenido.
    }

    // Por Id
    @GetMapping("/{id}")
    public ResponseEntity<Arma> getArmaById(@PathVariable int id) { //toma el {id} de la URL.
        return armasService.getArmaById(id)
                .map(ResponseEntity::ok)            // Si existe, responde 200(OK) con el arma
                .orElse(ResponseEntity.notFound().build());  // Si no existe, 404
    }

    //registrar
    @PostMapping
    public ResponseEntity<Arma> createArma(@RequestBody Arma arma) { //requestbody convierte el JSON que envías en un objeto Arma.
        Arma newArma = armasService.saveArma(arma);
        return ResponseEntity.status(HttpStatus.CREATED).body(newArma);
    }

    //Actualizar un arma
    @PutMapping("/{id}") //PUT: se usa para modificar.
    public ResponseEntity<Arma> updateArma(@PathVariable int id, @RequestBody Arma arma) {
        if (!armasService.existsById(id)) { // para revisar que el ID sea el correcto
            return ResponseEntity.notFound().build();
        }
        arma.setIdArma(id); // asegura que el id sea el correcto
        Arma updated = armasService.saveArma(arma);
        return ResponseEntity.ok(updated);
    }

    //Eliminar un arma
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArma(@PathVariable int id) {
        if (!armasService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        armasService.deleteArma(id);
        return ResponseEntity.noContent().build(); // sale un código que indica eliminado con éxito (204)
    }




}
