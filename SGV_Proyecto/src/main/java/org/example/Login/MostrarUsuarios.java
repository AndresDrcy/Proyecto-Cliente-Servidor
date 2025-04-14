package org.example.Login;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MostrarUsuarios extends JFrame {
    private JTable TablaUs;
    private JPanel PanelUs;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton salirButton;

    public  MostrarUsuarios(){

        setContentPane(PanelUs);
        setTitle("Inventario Actual");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        crearTabla();
        cargarDatosEnTabla();


        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int filaSeleccionada = TablaUs.getSelectedRow();

                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un usuario para editar");
                    return;
                }

                String idUsuario = (String) TablaUs.getValueAt(filaSeleccionada, 0);
                String usuarioActual = (String) TablaUs.getValueAt(filaSeleccionada, 1);
                String contrasenaActual = (String) TablaUs.getValueAt(filaSeleccionada, 2);
                String rolActual = (String) TablaUs.getValueAt(filaSeleccionada, 3);

                try {
                    String nuevoUsuario = JOptionPane.showInputDialog("Nuevo usuario:", usuarioActual);
                    String nuevaContrasena = JOptionPane.showInputDialog("Nueva contraseña:", contrasenaActual);
                    String nuevoRol = JOptionPane.showInputDialog("Nuevo rol:", rolActual);

                    Usuario.editarUsuario(idUsuario, nuevoUsuario, nuevaContrasena, nuevoRol);
                    cargarDatosEnTabla();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al editar. Verifique los datos");
                }
            }

        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = TablaUs.getSelectedRow();
                if (filaSeleccionada != -1) {
                    String idUsuario = (String) TablaUs.getValueAt(filaSeleccionada, 0);
                    int confirmar = JOptionPane.showConfirmDialog(null,
                            "Esta seguro de eliminar este registro?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);

                    if (confirmar == JOptionPane.YES_OPTION) {
                        Usuario.eliminarUsuario(idUsuario);
                        cargarDatosEnTabla();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila para eliminar");
                }
            }
        });


    }

    public void crearTabla() {
        TablaUs.setModel(new DefaultTableModel(
                null,
                new String[]{"codigoUsuario","Usuario", "Contraseña", "Rol"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

    }

    public void cargarDatosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) TablaUs.getModel();
        Usuario.cargarUsuariosEnTabla(modelo);
    }

}
