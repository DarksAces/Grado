package com.example.nostrafamiglia.controller;


import com.example.nostrafamiglia.model.EncargadoPedido;
import com.example.nostrafamiglia.service.EncargadoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lafamiglia/encargados")
@CrossOrigin(origins = "*")
public class EncargadoPedidoController {

    @Autowired
    private EncargadoPedidoService encargadoPedidoService;

    // GET
    @GetMapping
    public ResponseEntity<List<EncargadoPedido>> getAllEncargados() {
        return ResponseEntity.ok(encargadoPedidoService.getAllEncargados());
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<EncargadoPedido> getEncargadoById(@PathVariable Integer id) {
        Optional<EncargadoPedido> encargado = encargadoPedidoService.getEncargadoById(id);
        return encargado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POst
    @PostMapping
    public ResponseEntity<EncargadoPedido> createEncargado(@RequestBody EncargadoPedido encargadoPedido) {
        EncargadoPedido nuevo = encargadoPedidoService.saveEncargado(encargadoPedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // Put
    @PutMapping("/{id}")
    public ResponseEntity<EncargadoPedido> updateEncargado(@PathVariable Integer id, @RequestBody EncargadoPedido encargadoPedido) {
        if (!encargadoPedidoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        encargadoPedido.setIdEncargado(id);
        EncargadoPedido actualizado = encargadoPedidoService.saveEncargado(encargadoPedido);
        return ResponseEntity.ok(actualizado);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEncargado(@PathVariable Integer id) {
        if (!encargadoPedidoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        encargadoPedidoService.deleteEncargado(id);
        return ResponseEntity.noContent().build();
    }



}
