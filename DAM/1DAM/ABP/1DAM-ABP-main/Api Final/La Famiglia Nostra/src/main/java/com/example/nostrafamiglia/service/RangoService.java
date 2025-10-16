package com.example.nostrafamiglia.service;

import com.example.nostrafamiglia.dto.RangoDTO;
import com.example.nostrafamiglia.model.Rango;
import com.example.nostrafamiglia.repository.RangoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RangoService {

    @Autowired
    private RangoRepository rangoRepository;

    //obtener rangos
    public List<Rango> getAllRangos() {
        return rangoRepository.findAll();
    }
    // DTO
    public List<RangoDTO> getAllRangosDTO() {
        return rangoRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    private RangoDTO convertToDTO(Rango rango) {
        return new RangoDTO(rango.getIdRango(), rango.getRango());
    }

    //rangos por id
    public Optional<Rango> getRangoById(int id) { return rangoRepository.findById(id); }

    //guardar un rango nuevo
    public Rango saveRango(Rango rango) {
        return rangoRepository.save(rango);
    }

    // borrar un rango po id
    public void deleteRangoById(int id) {
        rangoRepository.deleteById(id);
    }

    // boolean para ver si existe lo que queramos boorar o modificar
    public boolean existRangoById(int id) { return rangoRepository.existsById(id); }


}
