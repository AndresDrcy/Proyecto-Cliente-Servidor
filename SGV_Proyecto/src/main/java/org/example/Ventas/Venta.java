package org.example.Ventas;

import org.example.Cliente.Cliente;
import org.example.Vehiculo;

import java.util.Date;

public class Venta {
    Cliente cliente;
    Vehiculo vehiculo;
    Date fechaVenta;
    double montoTotal;

    //Constructor
    public Venta(Cliente cliente, Vehiculo vehiculo, Date fechaVenta, double montoTotal){
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.fechaVenta = fechaVenta;
        this.montoTotal = montoTotal;
    }

    //Metodos de la clase
    public void procesarVenta(){
        System.out.println("Procesando venta...");
        System.out.println("Venta procesada para el cliente: " + cliente.nombre + " por un monto de: " + montoTotal);
    }

    public void mostrarDetallesVenta(){
        System.out.println("Detalles de venta:");
        System.out.println("Cliente: " + cliente.nombre);
        System.out.println("Vehiculo: " + vehiculo.modelo);
        System.out.println("Fecha: " + fechaVenta);
        System.out.println("Monto total: " + montoTotal);
    }
}
