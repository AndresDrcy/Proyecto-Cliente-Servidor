package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionBD {

    Connection conexion = null;
    PreparedStatement consulta = null;
    ResultSet resultado = null;
    String url = "jdbc:mysql://localhost:3306/sgv_proyecto";
    String usuario = "root";
    String contrasena = "1234";

    public void setConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("se realizó la conexión con la BD");
        } catch (SQLException | ClassNotFoundException error) {
            error.printStackTrace();
            System.out.println("no se realizó la conexión con la BD");
        }
    }

    public void setConsulta(String consultaSQL) {
        try {
            this.consulta = conexion.prepareStatement(consultaSQL);
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }

    public ResultSet getResultado() {
        try {
            return consulta.executeQuery();
        } catch (SQLException error) {
            error.printStackTrace();
            return null;
        }
    }

    public PreparedStatement getConsulta() {
        return consulta;
    }

    public void cerrarConexion() {
        try {
            if (resultado != null) resultado.close();
            if (consulta != null) consulta.close();
            if (conexion != null) conexion.close();
            System.out.println("se cerro la base de datos");
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }
}
