package org.example.demojdbc.model;

public class Producto {
    private int id;
    private String nombre;
    private double precio;  // Cambié 'edad' por 'precio'

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {  // Cambié 'edad' por 'precio'
        return precio;
    }

    public void setPrecio(double precio) {  // Cambié 'edad' por 'precio'
        this.precio = precio;
    }

    public Producto() {
    }

    public Producto(String nombre, double precio) {  // Cambié 'edad' por 'precio'
        this.nombre = nombre;
        this.precio = precio;
    }

    public Producto(String nombre) {
        this.nombre = nombre;
    }
}
