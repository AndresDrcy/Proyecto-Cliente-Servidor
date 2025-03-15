package org.example;
public class Motocicleta extends Vehiculo {
    //Atributos especificos
    int cilindrada;


    public Motocicleta(String marca, String modelo, String color, int anno, double precio, int cilindrada) {
        super(marca, modelo, color, anno, precio);
        this.cilindrada = cilindrada;
    }

    //Metodos Abstractos
    @Override
    public void mostrarDetalles() {
        System.out.println("motocicleta: "+ marca + " - " + modelo + " - " + anno + " - " + precio + "USD");
    }

    @Override
    public double calcularImpuesto() {
        return precio * 0.025;
    }
}
