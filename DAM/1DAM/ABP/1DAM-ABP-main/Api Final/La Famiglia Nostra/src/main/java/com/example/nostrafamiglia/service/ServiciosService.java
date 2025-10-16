package com.example.nostrafamiglia.service;

import com.example.nostrafamiglia.dto.ServiciosDTO;
import com.example.nostrafamiglia.model.Servicios;
import com.example.nostrafamiglia.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiciosService {
    @Autowired
    private ServicioRepository servicioRepository;

    //Get all
    public List<ServiciosDTO> getAllServiciosDTO() {
        return servicioRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }
    // Entidad a DTO
    private ServiciosDTO convertToDTO(Servicios s) {
        return new ServiciosDTO(
                s.getIdServicio(),
                capitalize(s.getNombreServicio()),
                capitalize(s.getDescripcion()),
                s.getPrecioServicio()
        );
    }

    private String capitalize(String texto) {
        if (texto == null || texto.isBlank()) return texto;
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }

    // ID
    public Optional<Servicios> findById(int id) {return servicioRepository.findById(id);}

    //Save o actu
    public Servicios save(Servicios servicio) {
        return servicioRepository.save(servicio);
    }

    // Borrar
    public void deleteServicio(int id) {servicioRepository.deleteById(id);}

    public boolean existsById(int id) {
        return servicioRepository.existsById(id);
    }
}
