package org.example;

public class Automovil extends Vehiculo{
    //atributos especificos de la clase

    int numeroPuertas;
    boolean tieneAireAcondicionado;
    //Constructor
    public Automovil(String marca, String modelo, String color, int anno, double precio, int numeroPuertas, boolean tieneAireAcondicionado, String tipo) {
        super(marca, modelo, color, anno, precio, tipo);
        this.numeroPuertas = numeroPuertas;
        this.tieneAireAcondicionado = tieneAireAcondicionado;
    }

    //Metodos Abstractos
    @Override
    public void mostrarDetalles() {
        System.out.println("Automovil: " + marca + " " + modelo + " - " + anno + " - " + precio + "USD" );
    }

    @Override
    public double calcularImpuesto() {
        return precio * 0.025;
    }
}
