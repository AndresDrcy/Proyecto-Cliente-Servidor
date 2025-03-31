package org.example.Cliente;

import org.example.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MostrarCliente extends JFrame {

    private JPanel InterfazMostrarCliente;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton salirButton;
    private JTable TablaClientes;
    private JPanel TituloClientes;

    public MostrarCliente() {
        setContentPane(InterfazMostrarCliente);
        setTitle("Clientes Actuales");
        setSize(600, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        crearTabla();
        cargarDatosDesdeBD();

        salirButton.addActionListener(e -> dispose());

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = TablaClientes.getSelectedRow();
                if (filaSeleccionada != -1) {
                    String documento = TablaClientes.getValueAt(filaSeleccionada, 1).toString();

                    int confirmar = JOptionPane.showConfirmDialog(
                            null,
                            "Esta seguro de eliminar al cliente con ID: " + documento + "?",
                            "Confirmar eliminacion",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirmar == JOptionPane.YES_OPTION) {
                        boolean exito = Cliente.eliminarCliente(documento);
                        if (exito) {
                            JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente.");
                            cargarDatosDesdeBD();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al eliminar cliente.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar.");
                }
            }
        });


        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = TablaClientes.getSelectedRow();
                if (filaSeleccionada != -1) {
                    String documento = TablaClientes.getValueAt(filaSeleccionada, 1).toString();
                    String nombreActual = TablaClientes.getValueAt(filaSeleccionada, 0).toString();
                    int edadActual = Integer.parseInt(TablaClientes.getValueAt(filaSeleccionada, 2).toString());
                    String telefonoActual = TablaClientes.getValueAt(filaSeleccionada, 3).toString();
                    String correoActual = TablaClientes.getValueAt(filaSeleccionada, 4).toString();

                    try {
                        String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre:", nombreActual);
                        int nuevaEdad = Integer.parseInt(JOptionPane.showInputDialog("Nueva edad:", edadActual));
                        String nuevoTelefono = JOptionPane.showInputDialog("Nuevo teléfono:", telefonoActual);
                        String nuevoCorreo = JOptionPane.showInputDialog("Nuevo correo:", correoActual);

                        boolean actualizado = Cliente.actualizarCliente(documento, nuevoNombre, nuevaEdad, nuevoTelefono, nuevoCorreo);

                        if (actualizado) {
                            JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente");
                            cargarDatosDesdeBD();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al actualizar el cliente");
                        }

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al editar. Verifique los datos");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para editar");
                }
            }
        });

    }

    private void crearTabla() {
        TablaClientes.setModel(new DefaultTableModel(
                null,
                new String[]{"Nombre", "ID", "Edad", "Teléfono", "Correo"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    private void cargarDatosDesdeBD() {
        DefaultTableModel modelo = (DefaultTableModel) TablaClientes.getModel();
        modelo.setRowCount(0);

        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta("SELECT * FROM at_cliente");
            ResultSet rs = conexion.getResultado();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getString("nombre"),
                        rs.getString("documento_identidad"),
                        rs.getInt("edad"),
                        rs.getString("telefono"),
                        rs.getString("correo")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos de clientes");
        } finally {
            conexion.cerrarConexion();
        }
    }
}
