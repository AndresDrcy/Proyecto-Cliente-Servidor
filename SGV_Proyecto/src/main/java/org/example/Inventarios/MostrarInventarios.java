package org.example.Inventarios;

import org.example.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                            "Está seguro de eliminar este registro?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

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
                            JOptionPane.showMessageDialog(null, "Error al eliminar el vehículo.");
                        } finally {
                            conexion.cerrarConexion();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila para eliminar.");
                }
            }
        });

        respaldarEnServidorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConexionBD conexion = new ConexionBD();
                conexion.setConexion();

                try {
                    // 1. Limpiar la tabla de respaldo
                    conexion.setConsulta("TRUNCATE TABLE at_inventarios_respaldo");
                    conexion.getConsulta().executeUpdate();

                    // 2. Leer inventario original
                    conexion.setConsulta("SELECT * FROM at_inventarios");
                    ResultSet rs = conexion.getResultado();

                    while (rs.next()) {
                        String insertSQL = "INSERT INTO at_inventarios_respaldo " +
                                "(codigo_unico, marca, modelo, color, anno, precio, tipo, numero_puertas, tiene_aire, cilindrada) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                        conexion.setConsulta(insertSQL);
                        PreparedStatement ps = conexion.getConsulta();

                        ps.setString(1, rs.getString("codigo_unico"));
                        ps.setString(2, rs.getString("marca"));
                        ps.setString(3, rs.getString("modelo"));
                        ps.setString(4, rs.getString("color"));
                        ps.setInt(5, rs.getInt("anno"));
                        ps.setDouble(6, rs.getDouble("precio"));
                        ps.setString(7, rs.getString("tipo"));

                        int puertas = rs.getInt("numero_puertas");
                        if (rs.wasNull()) ps.setNull(8, java.sql.Types.INTEGER);
                        else ps.setInt(8, puertas);

                        boolean aire = rs.getBoolean("tiene_aire");
                        if (rs.wasNull()) ps.setNull(9, java.sql.Types.BOOLEAN);
                        else ps.setBoolean(9, aire);

                        int cc = rs.getInt("cilindrada");
                        if (rs.wasNull()) ps.setNull(10, java.sql.Types.INTEGER);
                        else ps.setInt(10, cc);

                        ps.executeUpdate();
                    }

                    JOptionPane.showMessageDialog(null, "✅ Inventario respaldado exitosamente.");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "⚠️ Error al respaldar: " + ex.getMessage());
                } finally {
                    conexion.cerrarConexion();
                }
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = TablaInvent.getSelectedRow();

                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un vehículo para editar.");
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

                    JOptionPane.showMessageDialog(null, "Vehículo actualizado correctamente.");
                    cargarDatosEnTabla();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al editar. Verifique los datos.");
                }
            }
        });
    }

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
            JOptionPane.showMessageDialog(null, "Error al cargar los datos.");
        } finally {
            conexion.cerrarConexion();
        }
    }
}
