package com.example.nostrafamiglia.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.nostrafamiglia.dto.ContactoDTO;
import com.example.nostrafamiglia.model.Contacto;
import com.example.nostrafamiglia.service.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lafamiglia/contactos")
@CrossOrigin(origins = "*")
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    @GetMapping
    public ResponseEntity<List<ContactoDTO>> getAllContactos() {
        return ResponseEntity.ok(contactoService.getAllContactosDTO());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Contacto> getContactoById(@PathVariable int id) {
        return contactoService.getContactoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Contacto> createContacto(@RequestBody Contacto contacto) {
        Contacto nuevo = contactoService.saveContacto(contacto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contacto> updateContacto(@PathVariable int id, @RequestBody Contacto contacto) {
        if (!contactoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        contacto.setIdContacto(id);
        return ResponseEntity.ok(contactoService.saveContacto(contacto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContacto(@PathVariable int id) {
        if (!contactoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        contactoService.deleteContacto(id);
        return ResponseEntity.noContent().build();
    }


}
