package org.example;

public abstract class Vehiculo {
    //Atributos
    String marca;
    String modelo;
    String color;
    int anno;
    double precio;

    // Constructor
    public Vehiculo(String marca, String modelo, String color, int anno, double precio) {
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.anno = anno;
        this.precio = precio;
    }

    //Metodos Abstractos
    public abstract void mostrarDetalles();
    public abstract double calcularImpuesto();
}

