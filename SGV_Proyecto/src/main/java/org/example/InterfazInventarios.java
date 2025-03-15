package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InterfazInventarios extends JFrame{
    private JPanel InterfazInventario;
    private JComboBox DespleVehiculos;
    private JTable TablaInvent;
    private JLabel TituloInvent;
    private JScrollPane TablaTotal;

    public InterfazInventarios() {
        // Configuración de la ventana
        setContentPane(InterfazInventario);
        setTitle("Inventario Actual");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setVisible(true);
        crearTabla();

    }

    public void crearTabla() {
        //creamos la estructura de la tabla
        TablaInvent.setModel(new DefaultTableModel(
                null,
                new String[]{"Marca", "Modelo", "Color", "Año", "Precio"}
        ));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazInventarios());
    }

}
