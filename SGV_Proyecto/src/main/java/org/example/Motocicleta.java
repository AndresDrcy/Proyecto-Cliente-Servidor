package org.example;
public class Motocicleta extends Vehiculo {
    //Atributos especificos
    int cilindrada;


    public Motocicleta(int id, String marca, String modelo, String color, int anno, double precio, int cilindrada, String tipo) {
        super(id, marca, modelo, color, anno, precio, tipo);
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
