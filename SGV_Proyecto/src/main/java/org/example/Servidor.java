// Clase para el servidor
package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Thread {

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(5700)) {
            System.out.println("Servidor activo en puerto 5700...");

            while (true) {
                Socket socket = serverSocket.accept(); // aqui esperamos la conexion
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Leer todo el mensaje enviado por el cliente
                StringBuilder mensaje = new StringBuilder();
                String linea;
                while ((linea = entrada.readLine()) != null) {
                    mensaje.append(linea).append("\n");
                }

                System.out.println("Inventario recibido:\n" + mensaje);
                guardarEnArchivo(mensaje.toString());

                socket.close(); // cerramos
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }

    private void guardarEnArchivo(String contenido) {
        try {

            String ruta = "SGV_Proyecto/Archivos/inventario_respaldo.txt";

            FileWriter fw = new FileWriter(ruta, false); // false = sobrescribir
            fw.write(contenido);
            fw.close();

            System.out.println("Inventario respaldado en: " + ruta);
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo: " + e.getMessage());
        }
    }
}
