package org.example;
//prueba repo

import javax.swing.*;
import java.util.Date;

public class Principal_SGV {
    public static void main(String[] args) {

        Login login = new Login(); // llamamos al login

        // Esperamos hasta que el login sea exitoso
        while (!login.esAutenticado()) { //la variable en login debe cambiar a true
            // Continuar mostrando la ventana de login hasta que el usuario se autentique
            try {
                Thread.sleep(500); // Esperamos un poco antes de volver a revisar el estado
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }

        // Si la autenticación es correcta, pasamos al menú
        System.out.println("Login exitoso. Iniciando el sistema...");

        //Crear vehiculos
        Automovil auto = new Automovil("Toyota","Corolla","Negro",2020,23800,4,true);
        Motocicleta moto = new Motocicleta("Honda","CB500","Azul oscuro",2022,8900,500);

        //Crear cliente
        Cliente cliente = new Cliente("Andres","568599958",28,"88888888","notiene@notiene.com");

        //Crear venta
        Venta venta = new Venta(cliente,auto, new Date(), auto.precio);
        venta.procesarVenta();
        venta.mostrarDetallesVenta();

        //Crear inventario y agregar vehiculos
        Inventario inventario = new Inventario();
        inventario.agregarVehiculo(auto);
        inventario.agregarVehiculo(moto);

        //Mostrar inventario
        System.out.println("Vehiculos en inventario:");
        for (Vehiculo v : inventario.listarVehiculos()){
            v.mostrarDetalles();
        }
    }
}