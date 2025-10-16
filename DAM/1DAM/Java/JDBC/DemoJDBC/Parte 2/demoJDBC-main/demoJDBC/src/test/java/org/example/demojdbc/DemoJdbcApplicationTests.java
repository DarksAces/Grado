package org.example.demojdbc;

import org.example.demojdbc.model.Producto;
import org.example.demojdbc.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Sql({"/schema.sql"})
class DemoJdbcApplicationTests {

    @Autowired
    ProductoRepository repositorio;

    @Test
    void borrarTodos() {
        repositorio.borrarTodos();
        List<Producto> lista = repositorio.buscarTodos();
        assertEquals(0, lista.size());
    }

    @Test
    void insertarProducto() {
        Producto producto1 = new Producto("Pepe", 30.0);
        repositorio.insertar(producto1);
        Producto producto2 = new Producto("Juan", 28.0);
        repositorio.insertar(producto2);
        List<Producto> lista = repositorio.buscarTodos();
        assertEquals(2, lista.size());
        assertNotNull(repositorio.buscarUno("Pepe"));
        assertNotNull(repositorio.buscarUno("Juan"));
    }

    @Test
    void buscarTodos() {
        List<Producto> lista = repositorio.buscarTodos();
        assertEquals(2, lista.size());
    }

    @Test
    void borrarProducto() {
        Producto producto = new Producto("Pepe");
        repositorio.borrar(producto);

        List<Producto> lista = repositorio.buscarTodos();
        assertEquals(1, lista.size());
        assertNotNull(repositorio.buscarUno("Juan"));
    }

    @Test
    void buscarUno() {
        Producto producto = repositorio.buscarUno("Juan");
        assertNotNull(producto);
        assertEquals("Juan", producto.getNombre());
    }
}
