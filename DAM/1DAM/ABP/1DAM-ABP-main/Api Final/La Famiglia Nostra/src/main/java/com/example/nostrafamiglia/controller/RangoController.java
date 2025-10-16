package com.example.nostrafamiglia.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.nostrafamiglia.dto.RangoDTO;
import com.example.nostrafamiglia.model.Rango;
import com.example.nostrafamiglia.service.RangoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lafamiglia/rangos")
@CrossOrigin(origins = "*")
public class RangoController {

    @Autowired
    private RangoService rangoService;

    //get
    @GetMapping
    public ResponseEntity<List<RangoDTO>> getAllRangos() {
        return ResponseEntity.ok(rangoService.getAllRangosDTO());
    }
    //id
    @GetMapping("/{id}")
    public ResponseEntity<Rango> getRangoById(@PathVariable int id) {
        return rangoService.getRangoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // guarda con post
    @PostMapping
    public ResponseEntity<Rango> createRango(@RequestBody Rango rango) {
        return ResponseEntity.ok(rangoService.saveRango(rango));
    }
    //modifica
    @PutMapping("/{id}")
    public ResponseEntity<Rango> updateRango(@PathVariable int id, @RequestBody Rango rango) {
        if (!rangoService.existRangoById(id)) {
            return ResponseEntity.notFound().build();
        }
        rango.setIdRango(id);
        return ResponseEntity.ok(rangoService.saveRango(rango));
    }
    //borra
    @DeleteMapping("/{id}")
    public ResponseEntity<Rango> deleteRango(@PathVariable int id) {
        if (!rangoService.existRangoById(id)) {
            return ResponseEntity.notFound().build();
        }
        rangoService.deleteRangoById(id);
        return ResponseEntity.ok().build();
    }

}
