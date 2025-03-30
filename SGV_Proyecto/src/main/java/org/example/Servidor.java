package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Servidor extends Thread {

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(5700)) {
            System.out.println("Servidor activo en el puerto 5700...");

            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String mensaje = entrada.readLine();

                if ("RESPALDAR".equalsIgnoreCase(mensaje)) {
                    System.out.println("Se recibio una orden de respaldo");
                    realizarRespaldo();
                } else {
                    System.out.println("No se reconoce la solicitud: " + mensaje);
                }

                socket.close();
            }
        } catch (Exception e) {
            System.out.println("Se genero un error: " + e.getMessage());
        }
    }

    private void realizarRespaldo() {
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            //limpiamos la ultima tabla registrada
            conexion.setConsulta("TRUNCATE TABLE at_inventarios_respaldo");
            conexion.getConsulta().executeUpdate();

            //leemos el inventario actual
            conexion.setConsulta("SELECT * FROM at_inventarios");
            ResultSet resultado = conexion.getResultado();

            while (resultado.next()) {
                String insertar = "INSERT INTO at_inventarios_respaldo " +
                        "(codigo_unico, marca, modelo, color, anno, precio, tipo, numero_puertas, tiene_aire, cilindrada) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                conexion.setConsulta(insertar);
                PreparedStatement conectar= conexion.getConsulta();

                conectar.setString(1, resultado.getString("codigo_unico"));
                conectar.setString(2, resultado.getString("marca"));
                conectar.setString(3, resultado.getString("modelo"));
                conectar.setString(4, resultado.getString("color"));
                conectar.setInt(5, resultado.getInt("anno"));
                conectar.setDouble(6, resultado.getDouble("precio"));
                conectar.setString(7, resultado.getString("tipo"));


                //validamos si el campo es nulo ya que un campo dependiendo del modelo puede tener aire o ser moto con cc en motor
                //se pone uno u otro
                int puertas = resultado.getInt("numero_puertas");
                if (resultado.wasNull()) conectar.setNull(8, java.sql.Types.INTEGER); else conectar.setInt(8, puertas);

                boolean aire = resultado.getBoolean("tiene_aire");
                if (resultado.wasNull()) conectar.setNull(9, java.sql.Types.BOOLEAN); else conectar.setBoolean(9, aire);

                int cilindrada = resultado.getInt("cilindrada");
                if (resultado.wasNull()) conectar.setNull(10, java.sql.Types.INTEGER); else conectar.setInt(10, cilindrada);

                conectar.executeUpdate();
            }

            System.out.println("Respaldo completado...");

        } catch (SQLException ex) {
            System.out.println("Error durante el respaldo: " + ex.getMessage());
        } finally {
            conexion.cerrarConexion();
        }
    }
}
