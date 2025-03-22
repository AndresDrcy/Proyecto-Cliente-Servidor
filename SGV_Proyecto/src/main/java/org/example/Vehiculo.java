package org.example;

public abstract class Vehiculo {
    //Atributos
    public int id;
    public String marca;
    public String modelo;
    public String color;
    public int anno;
    public double precio;
    public String tipo;

    // Constructor
    public Vehiculo(int id, String marca, String modelo, String color, int anno, double precio, String tipo) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.anno = anno;
        this.precio = precio;
        this.tipo = tipo;
    }

    //Metodos Abstractos
    public abstract void mostrarDetalles();
    public abstract double calcularImpuesto();
}

