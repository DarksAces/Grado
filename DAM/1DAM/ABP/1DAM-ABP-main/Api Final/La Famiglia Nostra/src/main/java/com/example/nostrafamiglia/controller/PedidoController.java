package com.example.nostrafamiglia.controller;

import com.example.nostrafamiglia.model.Pedido;
import com.example.nostrafamiglia.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/lafamiglia/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> getAllPedidos() {
        return ResponseEntity.ok(pedidoService.getAllPedidos());
    }

    // ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable int id) {
        return pedidoService.getPedidoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //post
    @PostMapping
    public ResponseEntity<Pedido> addPedido(@RequestBody Pedido pedido) {
        Pedido newPedido = pedidoService.savePedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPedido);
    }

    //putt
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable int id, @RequestBody Pedido pedido) {
        if (!pedidoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pedido.setIdPedido(id); // importante para actualizar correctamente
        Pedido updatedPedido = pedidoService.savePedido(pedido);
        return ResponseEntity.ok(updatedPedido);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable int id) {
        if (!pedidoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }

}
