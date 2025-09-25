package com.example.nostrafamiglia.service;

import com.example.nostrafamiglia.model.EncargadoPedido;
import com.example.nostrafamiglia.repository.EncargadoPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EncargadoPedidoService {

    @Autowired
    private EncargadoPedidoRepository encargadoPedidoRepository;

    // Obtener todos los encargados
    public List<EncargadoPedido> getAllEncargados() {
        return encargadoPedidoRepository.findAll();
    }

    // Buscar por ID
    public Optional<EncargadoPedido> getEncargadoById(Integer id) {
        return encargadoPedidoRepository.findById(id);
    }

    // Guardar
    public EncargadoPedido saveEncargado(EncargadoPedido encargadoPedido) {
        return encargadoPedidoRepository.save(encargadoPedido);
    }

    // Borrar
    public void deleteEncargado(Integer id) {
        encargadoPedidoRepository.deleteById(id);
    }

    // Verificar existencia
    public boolean existsById(Integer id) {
        return encargadoPedidoRepository.existsById(id);
    }
}