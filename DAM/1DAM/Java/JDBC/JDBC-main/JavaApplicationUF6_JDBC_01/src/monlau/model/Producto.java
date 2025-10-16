package monlau.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un producto en el inventario
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    private Integer id;
    private String nombre;
    private Double precio;

    // Implementación manual del constructor si Lombok no está funcionando
    public Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + String.format("%.2f", precio) +
                "€}";
    }

    // Implementación manual de los getters y setters si Lombok no está funcionando
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio != null ? precio : 0.0;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getId() {
        return id != null ? id : 0;
    }
}