package org.example.Inventarios;

import org.example.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
;

public class Inventario {

    public void cargarVehiculosEnTabla(DefaultTableModel modelo) {
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();
        modelo.setRowCount(0);

        try {
            conexion.setConsulta("SELECT * FROM at_inventarios");
            ResultSet resultadoMostrar = conexion.getResultado();

            while (resultadoMostrar.next()) {
                modelo.addRow(new Object[]{
                        resultadoMostrar.getInt("id"),
                        resultadoMostrar.getString("marca"),
                        resultadoMostrar.getString("modelo"),
                        resultadoMostrar.getString("color"),
                        resultadoMostrar.getInt("anno"),
                        resultadoMostrar.getDouble("precio"),
                        resultadoMostrar.getString("tipo")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar inventario.");
        } finally {
            conexion.cerrarConexion();
        }
    }

    //Metodos de la clase

    public void eliminarVehiculo(int idVehiculo) {
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta("DELETE FROM at_inventarios WHERE id = ?");
            conexion.getConsulta().setInt(1, idVehiculo);
            conexion.getConsulta().executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar vehículo");
        } finally {
            conexion.cerrarConexion();
        }
    }


    public void editarVehiculo(int idVehiculo, String nuevaMarca, String nuevoModelo, String nuevoColor, int nuevoAnno, double nuevoPrecio, String tipoActual) {
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta("UPDATE at_inventarios SET marca = ?, modelo = ?, color = ?, anno = ?, precio = ?, tipo = ? WHERE id = ?");
            PreparedStatement resultadoActualizar = conexion.getConsulta();
            resultadoActualizar.setString(1, nuevaMarca);
            resultadoActualizar.setString(2, nuevoModelo);
            resultadoActualizar.setString(3, nuevoColor);
            resultadoActualizar.setInt(4, nuevoAnno);
            resultadoActualizar.setDouble(5, nuevoPrecio);
            resultadoActualizar.setInt(6, idVehiculo);
            resultadoActualizar.setString(7, tipoActual);
            resultadoActualizar.executeUpdate();
            JOptionPane.showMessageDialog(null, "Vehículo actualizado correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar vehículo");
        } finally {
            conexion.cerrarConexion();
        }
    }

    public void solicitarRespaldoAlServidor() {
        try {
            Socket socket = new Socket("localhost", 5700);
            socket.getOutputStream().write("RESPALDAR\n".getBytes());
            socket.getOutputStream().flush();//el flush es para enviar de una vez la peticion
            socket.close();
            JOptionPane.showMessageDialog(null, "Solicitud de respaldo enviada al servidor");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo contactar al servidor");
        }
    }
}

