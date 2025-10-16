package com.example.nostrafamiglia.service;
// spuente entre base de datros y controlador que define lo que la app hace con los datos (como los métodos de un objeto)
import com.example.nostrafamiglia.dto.ArmaDTO;
import com.example.nostrafamiglia.model.Arma;
import com.example.nostrafamiglia.repository.ArmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service // es una anotación de Spring que marca una clase como un componente de servicio. Con esto luego se podrá usar esta clase en otros lugares sin tener que crearla manualmente, solo con @Autowired
public class ArmaService {

    @Autowired //Anotación de Spring que le dice: Inyéctame automáticamente una instancia de esta clase
    private ArmaRepository armaRepository;

    // Obtener todas las armas de la base de datos
    public List<Arma> getAllArmas() {
        return armaRepository.findAll();
    }

    // devuelve solo DTO
    public List<ArmaDTO> getAllArmasDTO() {
        return armaRepository.findAll().stream() //Obtiene todas las armas de la base de datos y crea un Stream para recorrerlas.
                .map(this::convertToDTO) // lo comviertw a dto
                .toList(); // y esto lo hace en modo lista
    }

    // Conversión de entidad a DTO
    private ArmaDTO convertToDTO(Arma arma) {
        return new ArmaDTO(
                arma.getIdArma(),
                arma.getNombreArma(),
                arma.getTipo(),
                arma.getPrecio()
        );
    }

    // Obtener una sola arma por su Id
    public Optional<Arma> getArmaById(int id) { //Un envoltorio (wrapper) que puede contener o no un valor. Cuando buscas por ID (puede que no exista y dar error) devuelves Optional.empty().
        return armaRepository.findById(id);
    }

    // Guardar o actualizar un arma
    public Arma saveArma(Arma arma) { // hace referencia a un objeto de tipo arma para guardarlo o modificarlo
        arma.setNombreArma(capitalize(arma.getNombreArma()));
        return armaRepository.save(arma);
    }
    // creo un metodo para que todas las armas siempre tengan el mismo formato de entrada
    private String capitalize(String texto) {
        if (texto == null || texto.isBlank()) return texto; // si no hay texto que formatear no hace nada para evitar que genere errores
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase(); //la primera en mayus y las siguiente en minus
    }

    // Borrar un arma
    public void deleteArma(int id) { //esto es void porque solo borras un dato y no necesitas que te devuelva algo
        armaRepository.deleteById(id);
    }
    // Saber si existe un arma por ID (antes de editar/borrar)
    public boolean existsById(int id) { // boolean porque solo comprueba si existe o no
        return armaRepository.existsById(id);
    }


}
