package org.example.demojdbc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.example.demojdbc.model.Persona;
import org.springframework.jdbc.core.RowMapper;

public class PersonaMapper implements RowMapper<Persona> {
    @Override
    public Persona mapRow(ResultSet rs, int rowNum) throws SQLException {
        Persona persona = new Persona();
        persona.setId(rs.getInt("id"));
        persona.setNombre(rs.getString("nombre"));
        persona.setApellidos(rs.getString("apellidos"));
        persona.setEdad(rs.getInt("edad"));
        return persona;
    }
}
