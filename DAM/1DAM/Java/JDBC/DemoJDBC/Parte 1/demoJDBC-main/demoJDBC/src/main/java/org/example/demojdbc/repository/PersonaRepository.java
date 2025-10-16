package org.example.demojdbc.repository;

import java.util.List;

import org.example.demojdbc.model.Persona;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PersonaRepository {

    private JdbcTemplate plantilla;

    public PersonaRepository(JdbcTemplate plantilla) {
        this.plantilla = plantilla;
    }
    @Transactional
    public void insertar(Persona persona) {
        plantilla.update("insert into Personas (nombre, apellidos, edad) values (?,?,?)",
                persona.getNombre(), persona.getApellidos(), persona.getEdad());
    }

    public List<Persona> buscarTodos() {
        return plantilla.query("select * from Personas",new PersonaMapper());
    }

    public Persona buscarUno(String nombre) {
        List<Persona> resultados = plantilla.query("select * from Personas where nombre=?",new PersonaMapper(),nombre);
        if (resultados.isEmpty()) {
            System.out.println("No se encontró ningún resultado para: " + nombre);
            return null;
        } else {
            System.out.println("Encontrado: " + resultados.get(0));
            return resultados.get(0);
        }
    }
    @Transactional
    public void borrar(Persona persona) {
        plantilla.update("delete from Personas where nombre=?",persona.getNombre());
    }
    @Transactional
    public void borrarTodos() {
        plantilla.update("delete from Personas");
    }
}
