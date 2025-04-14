package org.example.Ventas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroVentas extends JFrame {
    private JTextField txtidcliente;
    private JLabel ClienteFL;
    private JLabel cedulaFL;
    private JLabel terminodepagoFL;
    private JTextField TxtIdVehiculo;
    private JButton registrarVentaButton;
    private JComboBox tipoPagoDsp;
    private JPanel panelVenta;
    private JButton salirButton;
    private JTextField txtnombrecliente;

    public RegistroVentas() {
        setContentPane(panelVenta);
        setTitle("Registro de Ventas");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        registrarVentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String documentoCliente = txtidcliente.getText().trim();
                String codigoVehiculo = TxtIdVehiculo.getText().trim();
                String formaPago = tipoPagoDsp.getSelectedItem().toString();

                if (documentoCliente.isEmpty() || codigoVehiculo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete todos los campos");
                    return;
                }

                Venta venta = new Venta(documentoCliente, codigoVehiculo, formaPago);

                if (venta.registrarEnBaseDeDatos()) {
                    JOptionPane.showMessageDialog(null,
                            "Venta registrada con exito\n\nVoucher: " + venta.getNumeroVoucher(),
                            "Confirmacion", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar la venta");
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
}
