package org.example.Inventarios;

import org.example.Vehiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MostrarInventarios extends JFrame{
    private final ArrayList<Vehiculo> listaInventario;
    private JPanel InterfazInventario;
    private JComboBox DespleVehiculos;
    private JTable TablaInvent;
    private JLabel TituloInvent;
    private JScrollPane TablaTotal;
    private JButton Salir;

    public MostrarInventarios(ArrayList<Vehiculo> listaInventario) {
        this.listaInventario = listaInventario;
        // Configuración de la ventana
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

    }

    public void crearTabla() {
        //creamos la estructura de la tabla
        TablaInvent.setModel(new DefaultTableModel(
                null,
                new String[]{"ID","Marca", "Modelo", "Color", "Año", "Precio", "Tipo"}
        ){
            // CON ESTO NO SE EDITA
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacemos que ninguna celda sea editable
            }
        });


        DespleVehiculos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //con esto podemos identificar lo que tiene seleccionado el jcombo
                String tipoSeleccion = DespleVehiculos.getSelectedItem().toString();

                //llamamos lo que tiene la tabla actualmente cargado
                DefaultTableModel TablaInventario = (DefaultTableModel) TablaInvent.getModel();

                //TableRowSorter este es como una especie de iterador por lineas
                TableRowSorter<DefaultTableModel> lecturainvet = new TableRowSorter<>(TablaInventario);
                TablaInvent.setRowSorter(lecturainvet);

                //con esto comparamos con el identificador del jcombo y filtramos la tabla
                lecturainvet.setRowFilter(RowFilter.regexFilter(tipoSeleccion, 6));

                //esto nada mas es para validar si se selecciona todo entonces mostrara todos los vehiculos
                if (tipoSeleccion.equals("Todos")) {
                    lecturainvet.setRowFilter(null);
                } else {
                    lecturainvet.setRowFilter(RowFilter.regexFilter(tipoSeleccion, 6));
                }
            }
        });
    }

    public void cargarDatosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) TablaInvent.getModel();

        for (Vehiculo v : listaInventario) {
            modelo.addRow(new Object[]{
                    v.id,
                    v.marca,
                    v.modelo,
                    v.color,
                    v.anno,
                    v.precio,
                    v.tipo
            });
        }

    }
}
