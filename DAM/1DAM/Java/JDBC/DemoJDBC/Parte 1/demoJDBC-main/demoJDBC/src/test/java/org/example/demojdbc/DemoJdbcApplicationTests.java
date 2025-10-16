package org.example.demojdbc;

import jakarta.transaction.Transactional;
import org.example.demojdbc.model.Persona;
import org.example.demojdbc.repository.PersonaRepository;
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
    PersonaRepository repositorio;

    @Test
    void borrarTodos() {
        repositorio.borrarTodos();
        List<Persona> lista = repositorio.buscarTodos();
        assertEquals(0, lista.size());
    }

    @Test
    void insertarPersona() {
        // Insertamos personas
        Persona persona1 = new Persona("Pepe", "Gonzalez", 30);
        repositorio.insertar(persona1);
        Persona persona2 = new Persona("Juan", "Martinez", 28);
        repositorio.insertar(persona2);

        // Verificamos si las personas fueron insertadas correctamente
        List<Persona> lista = repositorio.buscarTodos();
        assertEquals(2, lista.size());
        assertNotNull(repositorio.buscarUno("Pepe"));
        assertNotNull(repositorio.buscarUno("Juan"));
    }

    @Test
    void buscarTodos() {
        List<Persona> lista = repositorio.buscarTodos();
        assertEquals(2, lista.size());
    }

    @Test
    void borrarPersona() {
        Persona persona = new Persona("Pepe");
        repositorio.borrar(persona);

        // Verificamos que el tamaño de la lista se haya reducido
        List<Persona> lista = repositorio.buscarTodos();
        assertEquals(1, lista.size());
        assertNotNull(repositorio.buscarUno("Juan"));
    }

    @Test
    void buscarUno() {
        Persona persona = repositorio.buscarUno("Juan");
        assertNotNull(persona);
        assertEquals("Juan", persona.getNombre());
    }

}
