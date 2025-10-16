package com.example.nostrafamiglia.service;

import com.example.nostrafamiglia.dto.ContactoDTO;
import com.example.nostrafamiglia.model.Contacto;
import com.example.nostrafamiglia.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactoService {

    @Autowired
    private ContactoRepository contactoRepository;
// get normal
    public List<Contacto> getAllContactos() {
        return contactoRepository.findAll();
    }

    //Dto
    public List<ContactoDTO> getAllContactosDTO() {
        return contactoRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    private ContactoDTO convertToDTO(Contacto contacto) {
        return new ContactoDTO(
                contacto.getIdContacto(),
                capitalize(contacto.getFirstName()),
                capitalize(contacto.getLastName()),
                capitalize(contacto.getApodo()),
                contacto.getNumero(),
                capitalize(contacto.getCampoInteres())
        );
    }
    // id
    public Optional<Contacto> getContactoById(int id) {
        return contactoRepository.findById(id);
    }
    // guarda
    public Contacto saveContacto(Contacto contacto) {
        contacto.setFirstName(capitalize(contacto.getFirstName()));
        contacto.setLastName(capitalize(contacto.getLastName()));
        contacto.setApodo(capitalize(contacto.getApodo()));
        contacto.setCampoInteres(capitalize(contacto.getCampoInteres()));
        return contactoRepository.save(contacto);
    }
    //da formato
    private String capitalize(String texto) {
        if (texto == null || texto.isBlank()) return texto;
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }
    // borra
    public void deleteContacto(int id) {
        contactoRepository.deleteById(id);
    }
    //existe o no
    public boolean existsById(int id) {
        return contactoRepository.existsById(id);
    }


}
