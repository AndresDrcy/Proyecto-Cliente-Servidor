package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;

public class Servidor extends Thread {

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(5700)) {
            System.out.println("Servidor activo en puerto 5700...");

            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                StringBuilder mensaje = new StringBuilder();
                String linea;
                while ((linea = entrada.readLine()) != null) {
                    mensaje.append(linea).append("\n");
                }

                System.out.println("Inventario recibido:\n" + mensaje);
                guardarEnBaseDeDatos(mensaje.toString());

                socket.close();
            }

        } catch (Exception e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }

    private void guardarEnBaseDeDatos(String contenido) {
        try {
            ConexionBD conexionBD = new ConexionBD();
            conexionBD.setConexion();

            String sql = "INSERT INTO at_inventarios_respaldo (contenido) VALUES (?)";
            conexionBD.setConsulta(sql);
            PreparedStatement ps = conexionBD.getConsulta();
            ps.setString(1, contenido);
            ps.executeUpdate();

            conexionBD.cerrarConexion();
            System.out.println("Inventario respaldado en tabla at_inventarios_respaldo.");
        } catch (Exception e) {
            System.out.println("No se pudo guardar el respaldo en base de datos: " + e.getMessage());
        }
    }
}
