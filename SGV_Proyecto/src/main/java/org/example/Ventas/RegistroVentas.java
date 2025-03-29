package org.example.Ventas;

import org.example.Cliente.Cliente;
import org.example.ConexionBD;
import org.example.Vehiculo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class RegistroVentas extends JFrame {
    private JTextField textField1; // documento cliente
    private JTextField textField2; // código vehículo
    private JComboBox tipoPagoDsp;
    private JPanel panelVenta;
    private JButton procesarVentaButton;
    private JButton salirButton;
    private JLabel nombreFL;
    private JLabel cedulaFL;
    private JLabel terminodepagoFL;

    public RegistroVentas() {
        setContentPane(panelVenta);
        setTitle("Registro de Venta");
        setSize(400, 400);
        setVisible(true);

        procesarVentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String docCliente = textField1.getText().trim();
                String codigoVehiculo = textField2.getText().trim();
                String formaPago = tipoPagoDsp.getSelectedItem().toString();

                try {
                    // Buscar cliente
                    Cliente cliente = buscarClientePorDocumento(docCliente);
                    if (cliente == null) {
                        JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
                        return;
                    }

                    // Buscar vehículo
                    Vehiculo vehiculo = buscarVehiculoPorCodigo(codigoVehiculo);
                    if (vehiculo == null) {
                        JOptionPane.showMessageDialog(null, "Vehículo no encontrado.");
                        return;
                    }

                    // Crear venta
                    double monto = vehiculo.precio;
                    Venta venta = new Venta(cliente, vehiculo, new Date(), monto);
                    venta.terminoPago = formaPago;

                    // Insertar en base de datos
                    ConexionBD conexionBD = new ConexionBD();
                    conexionBD.setConexion();

                    String sql = "INSERT INTO at_ventas (numero_voucher, documento_cliente, codigo_vehiculo, fecha_venta, monto_total, termino_pago) VALUES (?, ?, ?, NOW(), ?, ?)";
                    conexionBD.setConsulta(sql);
                    PreparedStatement consulta = conexionBD.getConsulta();

                    consulta.setString(1, venta.getNumeroVoucher());
                    consulta.setString(2, docCliente);
                    consulta.setString(3, codigoVehiculo);
                    consulta.setDouble(4, monto);
                    consulta.setString(5, formaPago);

                    consulta.executeUpdate();
                    conexionBD.cerrarConexion();

                    JOptionPane.showMessageDialog(null, "✅ Venta registrada exitosamente con voucher: " + venta.getNumeroVoucher());
                    dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "❌ Error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private Cliente buscarClientePorDocumento(String doc) {
        try {
            ConexionBD conexionBD = new ConexionBD();
            conexionBD.setConexion();

            String sql = "SELECT * FROM at_cliente WHERE documento_identidad = ?";
            conexionBD.setConsulta(sql);
            PreparedStatement ps = conexionBD.getConsulta();
            ps.setString(1, doc);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Cliente(
                        rs.getString("nombre"),
                        rs.getString("documento_identidad"),
                        rs.getInt("edad"),
                        rs.getString("telefono"),
                        rs.getString("correo")
                );
            }

            conexionBD.cerrarConexion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Vehiculo buscarVehiculoPorCodigo(String codigo) {
        try {
            ConexionBD conexionBD = new ConexionBD();
            conexionBD.setConexion();

            String sql = "SELECT * FROM at_inventarios WHERE codigo_unico = ?";
            conexionBD.setConsulta(sql);
            PreparedStatement ps = conexionBD.getConsulta();
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Solo los atributos comunes
                return new Vehiculo(
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("color"),
                        rs.getInt("anno"),
                        rs.getDouble("precio"),
                        rs.getString("tipo")
                ) {
                    @Override
                    public void mostrarDetalles() {}

                    @Override
                    public double calcularImpuesto() { return 0; }
                };
            }

            conexionBD.cerrarConexion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
