package org.example.Cliente;

import org.example.ConexionBD;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Cliente {
    public String nombre;
    public String documentoIdentidad;
    public int edad;
    public String telefono;
    public String correo;

    public Cliente(String nombre, String documentoIdentidad, int edad, String telefono, String correo) {
        this.nombre = nombre;
        this.documentoIdentidad = documentoIdentidad;
        this.edad = edad;
        this.telefono = telefono;
        this.correo = correo;
    }

    public boolean registrarCliente() {
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();
        try {
            String ConsultaCliente = "INSERT INTO at_cliente (documento_identidad, nombre, edad, telefono, correo) VALUES (?, ?, ?, ?, ?)";
            conexion.setConsulta(ConsultaCliente);
            PreparedStatement Consulta = conexion.getConsulta();

            Consulta.setString(1, documentoIdentidad);
            Consulta.setString(2, nombre);
            Consulta.setInt(3, edad);
            Consulta.setString(4, telefono);
            Consulta.setString(5, correo);
            Consulta.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
            return false;
        } finally {
            conexion.cerrarConexion();
        }
    }

    public static boolean eliminarCliente(String documento) {
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();
        try {
            String consultaEliminar = "DELETE FROM at_cliente WHERE documento_identidad = ?";
            conexion.setConsulta(consultaEliminar);
            PreparedStatement consulta = conexion.getConsulta();
            consulta.setString(1, documento);
            consulta.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        } finally {
            conexion.cerrarConexion();
        }
    }

    public static boolean actualizarCliente(String documento, String nuevoNombre, int nuevaEdad, String nuevoTelefono, String nuevoCorreo) {
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();
        try {
            String consultaActualizar = "UPDATE at_cliente SET nombre = ?, edad = ?, telefono = ?, correo = ? WHERE documento_identidad = ?";
            conexion.setConsulta(consultaActualizar);
            PreparedStatement consulta = conexion.getConsulta();
            consulta.setString(1, nuevoNombre);
            consulta.setInt(2, nuevaEdad);
            consulta.setString(3, nuevoTelefono);
            consulta.setString(4, nuevoCorreo);
            consulta.setString(5, documento);
            consulta.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        } finally {
            conexion.cerrarConexion();
        }
    }
}
