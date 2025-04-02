package org.example;

import java.sql.PreparedStatement;

public class Motocicleta extends Vehiculo {
    //Atributos especificos
    int cilindrada;


    public Motocicleta(String marca, String modelo, String color, int anno, double precio, int cilindrada, String tipo) {
        super(marca, modelo, color, anno, precio, tipo);
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

    public void guardarEnBaseDeDatos() throws Exception {
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        String consulta = "INSERT INTO at_inventarios (codigo_unico, marca, modelo, color, anno, precio, tipo, cilindrada) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        conexion.setConsulta(consulta);
        PreparedStatement resultadoConsulta = conexion.getConsulta();
        resultadoConsulta.setString(1, generarCodigoUnico());
        resultadoConsulta.setString(2, marca);
        resultadoConsulta.setString(3, modelo);
        resultadoConsulta.setString(4, color);
        resultadoConsulta.setInt(5, anno);
        resultadoConsulta.setDouble(6, precio);
        resultadoConsulta.setString(7, tipo);
        resultadoConsulta.setInt(8, cilindrada);

        resultadoConsulta.executeUpdate();
        conexion.cerrarConexion();
    }

    public String generarCodigoUnico() {
        return "VH-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}

