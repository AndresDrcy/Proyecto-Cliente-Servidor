package org.example;

public class Cliente {
    //Atributos

    String nombre;
    String documentoIdentidad;
    int edad;
    String telefono;
    String correo;

    //Constructor
    public Cliente (String nombre, String documentoIdentidad, int edad, String telefono, String correo){
        this.nombre = nombre;
        this.documentoIdentidad = documentoIdentidad;
        this.edad = edad;
        this.telefono = telefono;
        this.correo = correo;
    }

    //Metodo de informacion
    public void mostrarInformacion(){
        System.out.println("Cliente: " + nombre + " Documento: " + documentoIdentidad + " Edad: " + edad + " Telefono: "+ telefono + " Correo: " + correo);
    }
}
