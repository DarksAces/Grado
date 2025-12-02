package com.example.supermarket;

public class Fruta {
    public int id;
    public String nombre;
    public int imagenResId; // ID del recurso drawable (R.drawable.manzana)
    public double precio;
    public int cantidad; // Elemento donde aparece la cantidad comprada

    public Fruta(int id, String nombre, int imagenResId, double precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.imagenResId = imagenResId;
        this.precio = precio;
        this.cantidad = cantidad;
    }
}
