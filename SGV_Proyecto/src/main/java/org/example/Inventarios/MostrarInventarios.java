package org.example.Inventarios;

import org.example.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;



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
                            "Esta seguro de eliminar este registro?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);

                    if (confirmar == JOptionPane.YES_OPTION) {
                        ConexionBD conexion = new ConexionBD();
                        conexion.setConexion();
                        try {
                            conexion.setConsulta("DELETE FROM at_inventarios WHERE id = ?");
                            conexion.getConsulta().setInt(1, idVehiculo);
                            conexion.getConsulta().executeUpdate();
                            JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente.");
                            cargarDatosEnTabla();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error al eliminar el vehículo");
                        } finally {
                            conexion.cerrarConexion();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila para eliminar");
                }
            }
        });

        respaldarEnServidorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Socket socket = new Socket("localhost", 5700);
                    socket.getOutputStream().write("RESPALDAR\n".getBytes());
                    socket.getOutputStream().flush();//el flush fuerza el envio de la peticion, asi no dura tanto
                    socket.close();

                    JOptionPane.showMessageDialog(null, "Solicitud de respaldo enviada al servidor");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "No se pudo contactar al servidor");
                    ex.printStackTrace();
                }
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

                try {
                    String nuevaMarca = JOptionPane.showInputDialog("Nueva marca:", marcaActual);
                    String nuevoModelo = JOptionPane.showInputDialog("Nuevo modelo:", modeloActual);
                    String nuevoColor = JOptionPane.showInputDialog("Nuevo color:", colorActual);
                    int nuevoAnno = Integer.parseInt(JOptionPane.showInputDialog("Nuevo año:", annoActual));
                    double nuevoPrecio = Double.parseDouble(JOptionPane.showInputDialog("Nuevo precio:", precioActual));

                    ConexionBD conexion = new ConexionBD();
                    conexion.setConexion();
                    conexion.setConsulta("UPDATE at_inventarios SET marca = ?, modelo = ?, color = ?, anno = ?, precio = ? WHERE id = ?");
                    conexion.getConsulta().setString(1, nuevaMarca);
                    conexion.getConsulta().setString(2, nuevoModelo);
                    conexion.getConsulta().setString(3, nuevoColor);
                    conexion.getConsulta().setInt(4, nuevoAnno);
                    conexion.getConsulta().setDouble(5, nuevoPrecio);
                    conexion.getConsulta().setInt(6, idVehiculo);
                    conexion.getConsulta().executeUpdate();

                    JOptionPane.showMessageDialog(null, "Vehículo actualizado correctamente");
                    cargarDatosEnTabla();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al editar. Verifique los datos");
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
        modelo.setRowCount(0); // Limpiar

        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta("SELECT * FROM at_inventarios");
            ResultSet rs = conexion.getResultado();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("color"),
                        rs.getInt("anno"),
                        rs.getDouble("precio"),
                        rs.getString("tipo")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos");
        } finally {
            conexion.cerrarConexion();
        }
    }
}
