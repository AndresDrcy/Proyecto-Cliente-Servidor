package org.example.Ventas;

import org.example.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Venta {
    private String documentoCliente;
    private String codigoVehiculo;
    private String terminoPago;
    private double montoTotal;
    private String numeroVoucher;
    private Date fechaVenta;

    public Venta(String documentoCliente, String codigoVehiculo, String terminoPago) {
        this.documentoCliente = documentoCliente;
        this.codigoVehiculo = codigoVehiculo;
        this.terminoPago = terminoPago;
        this.fechaVenta = new Date();// con esto podemos sacar la fecha del sistema, mejor que ingresarla
        this.numeroVoucher = generarNumeroVoucher();
    }

    private String generarNumeroVoucher() {
        return "VCH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public boolean registrarEnBaseDeDatos() {
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            //buscamos directamente de la base de datos el precio del vehiculo
            conexion.setConsulta("SELECT precio FROM at_inventarios WHERE codigo_unico = ?");
            PreparedStatement buscar = conexion.getConsulta();
            buscar.setString(1, codigoVehiculo);
            ResultSet resultado = buscar.executeQuery();

            if (!resultado.next()) {
                System.out.println("Vehículo no encontrado en inventario");
                return false;
            }

            montoTotal = resultado.getDouble("precio");

            //guardamos la venta en la tabla
            String insertSQL = "INSERT INTO at_ventas (numero_voucher, documento_cliente, codigo_vehiculo, fecha_venta, monto_total, termino_pago) " +
                    "VALUES (?, ?, ?, NOW(), ?, ?)";
            conexion.setConsulta(insertSQL);
            PreparedStatement insert = conexion.getConsulta();
            insert.setString(1, numeroVoucher);
            insert.setString(2, documentoCliente);
            insert.setString(3, codigoVehiculo);
            insert.setDouble(4, montoTotal);
            insert.setString(5, terminoPago);
            insert.executeUpdate();

            //cuando se realiza la venta es necesario eliminar el vehiculo del inventario
            conexion.setConsulta("DELETE FROM at_inventarios WHERE codigo_unico = ?");
            PreparedStatement eliminar = conexion.getConsulta();
            eliminar.setString(1, codigoVehiculo);
            eliminar.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar venta: " + e.getMessage());
            return false;
        } finally {
            conexion.cerrarConexion();
        }
    }

    public static ArrayList<Venta> obtenerHistorialVentas() {
        ArrayList<Venta> historial = new ArrayList<>();
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            String sql = "SELECT numero_voucher, documento_cliente, codigo_vehiculo, fecha_venta, monto_total, termino_pago FROM at_ventas";
            conexion.setConsulta(sql);
            ResultSet rs = conexion.getResultado();

            while (rs.next()) {
                Venta venta = new Venta(
                        rs.getString("documento_cliente"),
                        rs.getString("codigo_vehiculo"),
                        rs.getString("termino_pago")
                );
                venta.numeroVoucher = rs.getString("numero_voucher");
                venta.fechaVenta = rs.getTimestamp("fecha_venta");
                venta.montoTotal = rs.getDouble("monto_total");

                historial.add(venta);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener historial de ventas: " + e.getMessage());
        } finally {
            conexion.cerrarConexion();
        }

        return historial;
    }

    //traer todos los datos de la venta
    public String getDocumentoCliente() { return documentoCliente; }
    public String getCodigoVehiculo() { return codigoVehiculo; }
    public String getTerminoPago() { return terminoPago; }
    public double getMontoTotal() { return montoTotal; }
    public String getNumeroVoucher() { return numeroVoucher; }
    public Date getFechaVenta() { return fechaVenta; }
}
