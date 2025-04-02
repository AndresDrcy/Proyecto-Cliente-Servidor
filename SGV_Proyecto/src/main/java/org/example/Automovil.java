package org.example;

import java.sql.PreparedStatement;

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


    public void guardarEnBaseDeDatos() throws Exception {
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        String consulta = "INSERT INTO at_inventarios (codigo_unico, marca, modelo, color, anno, precio, tipo, numero_puertas, tiene_aire) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        conexion.setConsulta(consulta);
        PreparedStatement consultaResultado = conexion.getConsulta();
        consultaResultado.setString(1, generarCodigoUnico());
        consultaResultado.setString(2, marca);
        consultaResultado.setString(3, modelo);
        consultaResultado.setString(4, color);
        consultaResultado.setInt(5, anno);
        consultaResultado.setDouble(6, precio);
        consultaResultado.setString(7, tipo);
        consultaResultado.setInt(8, numeroPuertas);
        consultaResultado.setBoolean(9, tieneAireAcondicionado);

        consultaResultado.executeUpdate();
        conexion.cerrarConexion();
    }

    public String generarCodigoUnico() {
        return "VH-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
