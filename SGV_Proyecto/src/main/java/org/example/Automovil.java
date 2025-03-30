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

        String sql = "INSERT INTO at_inventarios (codigo_unico, marca, modelo, color, anno, precio, tipo, numero_puertas, tiene_aire) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        conexion.setConsulta(sql);
        PreparedStatement ps = conexion.getConsulta();
        ps.setString(1, generarCodigoUnico());
        ps.setString(2, marca);
        ps.setString(3, modelo);
        ps.setString(4, color);
        ps.setInt(5, anno);
        ps.setDouble(6, precio);
        ps.setString(7, tipo);
        ps.setInt(8, numeroPuertas);
        ps.setBoolean(9, tieneAireAcondicionado);

        ps.executeUpdate();
        conexion.cerrarConexion();
    }

    private String generarCodigoUnico() {
        return "VH-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
