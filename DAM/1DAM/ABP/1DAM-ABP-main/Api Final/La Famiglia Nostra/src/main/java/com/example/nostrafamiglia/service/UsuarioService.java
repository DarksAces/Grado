package com.example.nostrafamiglia.service;


import com.example.nostrafamiglia.model.Usuario;
import com.example.nostrafamiglia.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    //Get all
    public List<Usuario> getAllUsuarios() {return usuariosRepository.findAll();}

    // por Id
    public Optional<Usuario> getUsuarioById(Integer id) {return usuariosRepository.findById(id);}

    //Save
    public Usuario saveUsuario(Usuario usuario) {return usuariosRepository.save(usuario);}

    //Boprrar
    public void deleteUsuario(Integer id) {usuariosRepository.deleteById(id);}

    // Ver si existe
    public boolean existsById(Integer id) {return usuariosRepository.existsById(id);}

    //posible implementación de encriptación de las contras




}
