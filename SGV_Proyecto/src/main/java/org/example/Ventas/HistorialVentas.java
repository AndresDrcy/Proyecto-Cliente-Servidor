package org.example.Ventas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HistorialVentas extends JFrame {
    private JPanel mainPanel;
    private JTable tablaVentas;
    private JButton btnCerrar;
    private JScrollPane scrollPane;
    private JLabel TituloInvent;

    public HistorialVentas() {
        setContentPane(mainPanel);
        setTitle("Historial de Ventas");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        configurarTabla();

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void configurarTabla() {
        String[] columnNames = {"Voucher", "Cliente", "Vehículo", "Fecha Venta", "Monto", "Pago"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        ArrayList<Venta> ventas = Venta.obtenerHistorialVentas();

        for (Venta v : ventas) {
            Object[] fila = {
                    v.getNumeroVoucher(),
                    v.getDocumentoCliente(),
                    v.getCodigoVehiculo(),
                    sdf.format(v.getFechaVenta()),
                    v.getMontoTotal(),
                    v.getTerminoPago()
            };
            model.addRow(fila);
        }

        tablaVentas.setModel(model);
    }
}
