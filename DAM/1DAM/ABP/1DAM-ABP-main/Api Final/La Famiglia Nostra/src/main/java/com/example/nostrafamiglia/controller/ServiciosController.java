package com.example.nostrafamiglia.controller;

import com.example.nostrafamiglia.dto.ServiciosDTO;
import com.example.nostrafamiglia.model.Servicios;
import com.example.nostrafamiglia.service.ServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController // indica que esta clase es un controlador REST
@RequestMapping("/lafamiglia/servicios") // todas las rutas de esta clase comienzan por esta URL
@CrossOrigin(origins = "*")
public class ServiciosController {

    @Autowired
    private ServiciosService serviciosService;

    // GET de DTO
    @GetMapping
    public ResponseEntity<List<ServiciosDTO>> getAllServicios() {
        return ResponseEntity.ok(serviciosService.getAllServiciosDTO());
    }

    // ID
    @GetMapping("/{id}")
    public ResponseEntity<Servicios> getServicioById(@PathVariable int id) {
        return serviciosService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST
    @PostMapping
    public ResponseEntity<Servicios> createServicio(@RequestBody Servicios servicio) {
        Servicios newServicio = serviciosService.save(servicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(newServicio);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<Servicios> updateServicio(@PathVariable int id, @RequestBody Servicios servicio) {
        if (!serviciosService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        servicio.setIdServicio(id);
        Servicios updated = serviciosService.save(servicio);
        return ResponseEntity.ok(updated);
    }

    // Borrar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicio(@PathVariable int id) {
        if (!serviciosService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        serviciosService.deleteServicio(id);
        return ResponseEntity.noContent().build();
    }
}
