package org.example.Ventas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistorialVentas extends JFrame {
    private JPanel mainPanel;
    private JTable tablaVentas;
    private JButton btnCerrar;
    private JScrollPane scrollPane;
    private JLabel TituloInvent;
    private JButton editarRegistroButton;
    private JButton eliminarRegistroButton;

    public HistorialVentas() {
        setContentPane(mainPanel);
        setTitle("Historial de Ventas");
        setSize(800, 400);
        setLocationRelativeTo(null);

        configurarTabla();

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        editarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaVentas.getSelectedRow();

                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un registro de venta para editar");
                    return;
                }

                String voucherActual = (String) tablaVentas.getValueAt(filaSeleccionada, 0);
                String clienteActual = (String) tablaVentas.getValueAt(filaSeleccionada, 1);
                String VehiculoActual = (String) tablaVentas.getValueAt(filaSeleccionada, 2);
                Date fechaActual = null;
                double montoActual = (double) tablaVentas.getValueAt(filaSeleccionada, 4);
                String pagoActual = (String) tablaVentas.getValueAt(filaSeleccionada, 5);

                try {
                    String nuevoCliente = JOptionPane.showInputDialog("Nuevo Cliente:", clienteActual);
                    String nuevoVehiculo = JOptionPane.showInputDialog("Nuevo Vehiculo:", VehiculoActual);
                    double nuevoMonto = Double.parseDouble(JOptionPane.showInputDialog("Nuevo Monto:", montoActual));
                    String nuevoTipo = JOptionPane.showInputDialog("Nuevo Tipo Pago:", pagoActual);

                    Venta.editarVenta(voucherActual, nuevoCliente, nuevoVehiculo, nuevoMonto, nuevoTipo);
                    configurarTabla();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al editar. Verifique los datos.");
                }
            }
        });

        eliminarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaVentas.getSelectedRow();
                if (filaSeleccionada != -1) {
                    String idVenta = (String) tablaVentas.getValueAt(filaSeleccionada, 0);
                    int confirmar = JOptionPane.showConfirmDialog(null,
                            "Esta seguro de eliminar este registro?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);

                    if (confirmar == JOptionPane.YES_OPTION) {
                        Venta.eliminarVenta(idVenta);
                        configurarTabla();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila para eliminar");
                }
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
