package org.example.Ventas;

import org.example.Cliente.Cliente;
import org.example.Vehiculo;

import java.util.Date;
import java.util.UUID;

public class Venta {
    public Cliente cliente;
    public String idCliente;
    public Vehiculo vehiculo;
    public Date fechaVenta;
    public double montoTotal;
    public String terminoPago;
    public String numeroVoucher; // ← nuevo campo

    // Constructor
    public Venta(Cliente cliente, Vehiculo vehiculo, Date fechaVenta, double montoTotal) {
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.fechaVenta = fechaVenta;
        this.montoTotal = montoTotal;
        this.numeroVoucher = generarNumeroVoucher(); // ← generado automáticamente
    }

    private String generarNumeroVoucher() {
        return "VCH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public String getNumeroVoucher() {
        return numeroVoucher;
    }

    // Métodos
    public void procesarVenta() {
        System.out.println("Procesando venta...");
        System.out.println("Venta procesada para el cliente: " + cliente.nombre + " por un monto de: " + montoTotal);
    }

    public void mostrarDetallesVenta() {
        System.out.println("Detalles de venta:");
        System.out.println("Voucher: " + numeroVoucher);
        System.out.println("Cliente: " + cliente.nombre);
        System.out.println("Vehículo: " + vehiculo.modelo);
        System.out.println("Fecha: " + fechaVenta);
        System.out.println("Monto total: " + montoTotal);
        System.out.println("Término de pago: " + terminoPago);
    }
}
