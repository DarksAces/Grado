package com.example.nostrafamiglia.service;


import com.example.nostrafamiglia.model.Pedido;
import com.example.nostrafamiglia.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    // get
    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    // ID
    public Optional<Pedido> getPedidoById(Integer id) {
        return pedidoRepository.findById(id);
    }

    // guardar
    public Pedido savePedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    // delete
    public void deletePedido(Integer id) {
        pedidoRepository.deleteById(id);
    }

    // hay o no hay pedido
    public boolean existsById(Integer id) {
        return pedidoRepository.existsById(id);
    }

}
