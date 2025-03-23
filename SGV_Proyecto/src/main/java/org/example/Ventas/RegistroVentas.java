package org.example.Ventas;
import org.example.Cliente.Cliente;
import org.example.Vehiculo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class RegistroVentas extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton procesarVentaButton;
    private JComboBox tipoPagoDsp;
    private JPanel panelVenta;
    private JButton salirButton;


    public RegistroVentas(ArrayList<Venta> registroVentas, ArrayList<Cliente> listaClientes, ArrayList<Vehiculo> listaInventario){

        setContentPane(panelVenta);
        setTitle("Interfaz Inventarios");
        setSize(400,400);
        setVisible(true);

        procesarVentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idCliente = textField1.getText().trim();
                String idVehiculoStr = textField2.getText().trim();
                String formaPago = tipoPagoDsp.getSelectedItem().toString();

                // Buscar cliente
                Cliente cliente = null;
                for (Cliente c : listaClientes) {
                    if (c.documentoIdentidad.equals(idCliente)) {
                        cliente = c;
                        break;
                    }
                }

                if (cliente == null) {
                    JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
                    return;
                }

                // Buscar vehículo
                Vehiculo vehiculo = null;
                try {
                    int idVehiculo = Integer.parseInt(idVehiculoStr);
                    for (Vehiculo v : listaInventario) {
                        if (v.id == idVehiculo) {
                            vehiculo = v;
                            break;
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID de vehículo inválido.");
                    return;
                }

                if (vehiculo == null) {
                    JOptionPane.showMessageDialog(null, "Vehículo no encontrado.");
                    return;
                }

                // Crear venta
                double monto = vehiculo.precio;
                Venta venta = new Venta(cliente, vehiculo, new Date(), monto);
                venta.terminoPago = formaPago;

                registroVentas.add(venta);

                JOptionPane.showMessageDialog(null, "✅ Venta registrada correctamente.");
                dispose();
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
