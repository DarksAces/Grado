package org.example.demojdbc.repository;

import java.util.List;
import org.example.demojdbc.model.Producto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductoRepository {

    private JdbcTemplate plantilla;

    public ProductoRepository(JdbcTemplate plantilla) {
        this.plantilla = plantilla;
    }

    @Transactional
    public void insertar(Producto producto) {
        // Actualizamos la inserción para que reciba el nombre y precio
        plantilla.update("insert into producto (nombre, precio) values (?,?)",
                producto.getNombre(), producto.getPrecio());
    }

    public List<Producto> buscarTodos() {
        return plantilla.query("select * from producto", new ProductoMapper());
    }

    public Producto buscarUno(String nombre) {
        List<Producto> resultados = plantilla.query("select * from producto where nombre=?", new ProductoMapper(), nombre);
        if (resultados.isEmpty()) {
            System.out.println("No se encontró ningún resultado para: " + nombre);
            return null;
        } else {
            System.out.println("Encontrado: " + resultados.get(0));
            return resultados.get(0);
        }
    }

    @Transactional
    public void borrar(Producto producto) {
        plantilla.update("delete from producto where nombre=?", producto.getNombre());
    }

    @Transactional
    public void borrarTodos() {
        plantilla.update("delete from producto");
    }
}
