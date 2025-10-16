package com.example.nostrafamiglia.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.nostrafamiglia.model.Usuario;
import com.example.nostrafamiglia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lafamiglia/usuarios")
@CrossOrigin(origins = "*")
public class UsuariosController {

    @Autowired
    private UsuarioService usuariosService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return ResponseEntity.ok(usuariosService.getAllUsuarios());
    }

    // ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable int id) {
        return usuariosService.getUsuarioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Post
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario newUsuario = usuariosService.saveUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUsuario);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuarios(@PathVariable int id, @RequestBody Usuario usuarios) {
        if (!usuariosService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarios.setIdUsuario(id);
        Usuario updated = usuariosService.saveUsuario(usuarios);
        return ResponseEntity.ok(updated);
    }

    // Borrar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable int id) {
        if (!usuariosService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuariosService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }



}
