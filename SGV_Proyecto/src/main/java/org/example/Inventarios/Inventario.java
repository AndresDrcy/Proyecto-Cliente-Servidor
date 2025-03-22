package org.example.Inventarios;

import org.example.Vehiculo;

import java.util.ArrayList;
import java.util.List;

public class Inventario {

    //lista de vehiculos
    List<Vehiculo> vehiculos = new ArrayList<>();

    public Inventario() {
        this.vehiculos = new ArrayList<>();
    }

    //Metodos de la clase
    public void agregarVehiculo (Vehiculo vehiculo){
        vehiculos.add(vehiculo);
        System.out.println("Vehiculo agregado: " + vehiculo.modelo);
    }

    public void eliminarVehiculo(Vehiculo vehiculo){
        vehiculos.remove(vehiculo);
        System.out.println("Vehiculo eliminado: "+ vehiculo.modelo);
    }


    public List<Vehiculo> listarVehiculos(){
        return vehiculos;
    }
}
