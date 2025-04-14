package org.example.Login;

import org.example.ConexionBD;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    public String usuario;
    public String contrasena;
    public String rol;

    public Usuario(String usuario, String contrasena, String rol) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public void guardarEnBaseDeDatos() throws Exception {
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        String consulta = "INSERT INTO at_usuarios (codigoUsuario,usuario, contrasena, rol) " +
                "VALUES (?, ?, ?, ?)";

        conexion.setConsulta(consulta);
        PreparedStatement consultaResultado = conexion.getConsulta();
        consultaResultado.setString(1, generarCodigoUnico());
        consultaResultado.setString(2, usuario);
        consultaResultado.setString(3, contrasena);
        consultaResultado.setString(4, rol);

        consultaResultado.executeUpdate();
        conexion.cerrarConexion();
        JOptionPane.showMessageDialog(null, "Usuario Agregado de forma correcta");
    }

    public static void cargarUsuariosEnTabla(DefaultTableModel modelo) {
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();
        modelo.setRowCount(0);

        try {
            conexion.setConsulta("SELECT * FROM at_usuarios");
            ResultSet resultadoMostrar = conexion.getResultado();

            while (resultadoMostrar.next()) {
                modelo.addRow(new Object[]{
                        resultadoMostrar.getString("codigoUsuario"),
                        resultadoMostrar.getString("usuario"),
                        resultadoMostrar.getString("contrasena"),
                        resultadoMostrar.getString("rol"),

                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar usuarios");
        } finally {
            conexion.cerrarConexion();
        }
    }
    public static void eliminarUsuario(String idUsuario) {
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta("DELETE FROM at_usuarios WHERE codigoUsuario = ?");
            conexion.getConsulta().setString(1, idUsuario);
            conexion.getConsulta().executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar al usuario");
        } finally {
            conexion.cerrarConexion();
        }
    }

    public static void editarUsuario(String idUsuario, String Nuevousuario, String Nuevacontrasena, String Nuevorol) {
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta("UPDATE at_usuarios SET usuario = ?,contrasena = ?,rol = ? WHERE codigoUsuario = ?");
            PreparedStatement resultadoActualizar = conexion.getConsulta();
            resultadoActualizar.setString(1, Nuevousuario);
            resultadoActualizar.setString(2, Nuevacontrasena);
            resultadoActualizar.setString(3, Nuevorol);
            resultadoActualizar.setString(4, idUsuario); // lo colocamos en el where
            resultadoActualizar.executeUpdate();
            JOptionPane.showMessageDialog(null, "usuario actualizado correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar Usuario");
        } finally {
            conexion.cerrarConexion();
        }
    }

    public String generarCodigoUnico() {
        return "US-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
