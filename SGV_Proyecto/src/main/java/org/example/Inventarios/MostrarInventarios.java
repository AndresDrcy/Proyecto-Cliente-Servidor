package org.example.Inventarios;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class MostrarInventarios extends JFrame {

    private JPanel InterfazInventario;
    private JComboBox DespleVehiculos;
    private JTable TablaInvent;
    private JLabel TituloInvent;
    private JScrollPane TablaTotal;
    private JButton Salir;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton respaldarEnServidorButton;

    private final Inventario inventario = new Inventario();

    public MostrarInventarios() {
        setContentPane(InterfazInventario);
        setTitle("Inventario Actual");
        setSize(600, 400);
        setVisible(true);

        crearTabla();
        cargarDatosEnTabla();

        Salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = TablaInvent.getSelectedRow();
                if (filaSeleccionada != -1) {
                    int idVehiculo = (int) TablaInvent.getValueAt(filaSeleccionada, 0);
                    int confirmar = JOptionPane.showConfirmDialog(null,
                            "Está seguro de eliminar este registro?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                    if (confirmar == JOptionPane.YES_OPTION) {
                        inventario.eliminarVehiculo(idVehiculo);
                        cargarDatosEnTabla();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila para eliminar");
                }
            }
        });

        respaldarEnServidorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventario.solicitarRespaldoAlServidor();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = TablaInvent.getSelectedRow();

                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un vehículo para editar");
                    return;
                }

                int idVehiculo = (int) TablaInvent.getValueAt(filaSeleccionada, 0);
                String marcaActual = (String) TablaInvent.getValueAt(filaSeleccionada, 1);
                String modeloActual = (String) TablaInvent.getValueAt(filaSeleccionada, 2);
                String colorActual = (String) TablaInvent.getValueAt(filaSeleccionada, 3);
                int annoActual = (int) TablaInvent.getValueAt(filaSeleccionada, 4);
                double precioActual = (double) TablaInvent.getValueAt(filaSeleccionada, 5);
                String tipoActual = (String) TablaInvent.getValueAt(filaSeleccionada, 6);

                try {
                    String nuevaMarca = JOptionPane.showInputDialog("Nueva marca:", marcaActual);
                    String nuevoModelo = JOptionPane.showInputDialog("Nuevo modelo:", modeloActual);
                    String nuevoColor = JOptionPane.showInputDialog("Nuevo color:", colorActual);
                    int nuevoAnno = Integer.parseInt(JOptionPane.showInputDialog("Nuevo año:", annoActual));
                    double nuevoPrecio = Double.parseDouble(JOptionPane.showInputDialog("Nuevo precio:", precioActual));
                    String nuevoTipo= JOptionPane.showInputDialog("Nuevo tipo:", tipoActual);

                    inventario.editarVehiculo(idVehiculo, nuevaMarca, nuevoModelo, nuevoColor, nuevoAnno, nuevoPrecio, tipoActual);
                    cargarDatosEnTabla();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al editar. Verifique los datos.");
                }
            }
        });
    }

    //no se toman todos los datos de la base de datos solo algunos
    //esto porque para no cambiar el objeto
    public void crearTabla() {
        TablaInvent.setModel(new DefaultTableModel(
                null,
                new String[]{"ID", "Marca", "Modelo", "Color", "Año", "Precio", "Tipo"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        DespleVehiculos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoSeleccion = DespleVehiculos.getSelectedItem().toString();
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) TablaInvent.getModel());
                TablaInvent.setRowSorter(sorter);

                if (tipoSeleccion.equals("Todos")) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(tipoSeleccion, 6));
                }
            }
        });
    }

    public void cargarDatosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) TablaInvent.getModel();
        inventario.cargarVehiculosEnTabla(modelo);
    }
}
