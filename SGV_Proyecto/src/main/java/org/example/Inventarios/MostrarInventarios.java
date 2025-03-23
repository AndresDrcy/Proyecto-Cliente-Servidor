package org.example.Inventarios;

import org.example.Automovil;
import org.example.Motocicleta;
import org.example.Vehiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MostrarInventarios extends JFrame{
    private final ArrayList<Vehiculo> listaInventario;
    private JPanel InterfazInventario;
    private JComboBox DespleVehiculos;
    private JTable TablaInvent;
    private JLabel TituloInvent;
    private JScrollPane TablaTotal;
    private JButton Salir;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton respaldarEnServidorButton;

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

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = TablaInvent.getSelectedRow();
                if (filaSeleccionada != -1) {
                    int tablaitem = TablaInvent.convertRowIndexToModel(filaSeleccionada);

                    int confirmar = JOptionPane.showConfirmDialog(null,
                            "Esta segura(o) de eliminar este registro?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);

                    if (confirmar == JOptionPane.YES_OPTION) {
                        listaInventario.remove(tablaitem);

                        DefaultTableModel tabla = (DefaultTableModel) TablaInvent.getModel();
                        tabla.removeRow(tablaitem);

                        JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila para eliminar.");
                }
            }
        });

        respaldarEnServidorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaInventario.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay vehículos para respaldar.");
                    return;
                }

                try {
                    Socket socket = new Socket("localhost", 5700);
                    PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

                    //estructura para el archivo
                    StringBuilder mensaje = new StringBuilder();
                    mensaje.append("RESPALDO DE INVENTARIO:\n\n");

                    for (Vehiculo v : listaInventario) {
                        String tipo = (v instanceof org.example.Automovil) ? "Automovil" :
                                (v instanceof org.example.Motocicleta) ? "Motocicleta" : "Desconocido";

                        mensaje.append(String.format(
                                "[%s] ID: %d | Marca: %s | Modelo: %s | Color: %s | Año: %d | Precio: %.2f\n",
                                tipo, v.id, v.marca, v.modelo, v.color, v.anno, v.precio
                        ));
                    }

                    // Enviar mensaje al servidor
                    salida.println(mensaje.toString());
                    socket.close();

                    JOptionPane.showMessageDialog(null, "Inventario respaldado correctamente.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "No se pudo conectar con el servidor.");
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

                int filaEnModelo = TablaInvent.convertRowIndexToModel(filaSeleccionada);
                Vehiculo vehiculo = listaInventario.get(filaEnModelo);

                try {
                    String nuevaMarca = JOptionPane.showInputDialog("Nueva marca:", vehiculo.marca);
                    String nuevoModelo = JOptionPane.showInputDialog("Nuevo modelo:", vehiculo.modelo);
                    String nuevoColor = JOptionPane.showInputDialog("Nuevo color:", vehiculo.color);
                    int nuevoAnno = Integer.parseInt(JOptionPane.showInputDialog("Nuevo año:", vehiculo.anno));
                    double nuevoPrecio = Double.parseDouble(JOptionPane.showInputDialog("Nuevo precio:", vehiculo.precio));

                    vehiculo.marca = nuevaMarca;
                    vehiculo.modelo = nuevoModelo;
                    vehiculo.color = nuevoColor;
                    vehiculo.anno = nuevoAnno;
                    vehiculo.precio = nuevoPrecio;

                    // Refrescar tabla
                    ((DefaultTableModel) TablaInvent.getModel()).removeRow(filaEnModelo);
                    ((DefaultTableModel) TablaInvent.getModel()).insertRow(filaEnModelo, new Object[]{
                            vehiculo.id,
                            vehiculo.marca,
                            vehiculo.modelo,
                            vehiculo.color,
                            vehiculo.anno,
                            vehiculo.precio,
                            vehiculo instanceof Automovil ? "Automovil" :
                                    vehiculo instanceof Motocicleta ? "Motocicleta" : "Desconocido"
                    });

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al editar. Verifique los datos.");
                }
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
