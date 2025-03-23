package org.example.Inventarios;

import org.example.Automovil;
import org.example.Motocicleta;
import org.example.Vehiculo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AgregarRegistroInventario extends JFrame {
    private JPanel PanelAgregar;
    private JTextField idvehiculoFil;
    private JButton agregarAlInventarioButton;
    private JTextField marcavehiculoFil;
    private JTextField modelovehiculoFil;
    private JTextField colorvehiculoFil;
    private JTextField añovehiculoFil;
    private JTextField preciovehiculoFil;
    private JComboBox listaTipojc;
    private JButton salirButton;


    public AgregarRegistroInventario(ArrayList<Vehiculo> listaInventario) {
        setContentPane(PanelAgregar);
        setTitle("Agregar Registros Inventarios"); // Nombre de la Ventana
        setSize(400, 400); // Tamaño de la ventana
        setVisible(true);

        agregarAlInventarioButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idvehiculoFil.getText());
                String marca = marcavehiculoFil.getText();
                String modelo = modelovehiculoFil.getText();
                String color = colorvehiculoFil.getText();
                int anno = Integer.parseInt(añovehiculoFil.getText());
                double precio = Double.parseDouble(preciovehiculoFil.getText());
                String tipo = listaTipojc.getSelectedItem().toString();

                Vehiculo nuevoVehiculo;

                if (tipo.equalsIgnoreCase("Automovil")) {
                    // Podés pedir número de puertas y si tiene aire en campos adicionales
                    int numeroPuertas = Integer.parseInt(JOptionPane.showInputDialog("Número de puertas:"));
                    boolean tieneAire = JOptionPane.showConfirmDialog(null, "¿Tiene aire acondicionado?", "Aire Acondicionado",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

                    nuevoVehiculo = new Automovil(id, marca, modelo, color, anno, precio, numeroPuertas, tieneAire, tipo);

                } else if (tipo.equalsIgnoreCase("Motocicleta")) {
                    int cilindrada = Integer.parseInt(JOptionPane.showInputDialog("Cilindrada (cc):"));
                    nuevoVehiculo = new Motocicleta(id, marca, modelo, color, anno, precio, cilindrada, tipo);

                } else {
                    JOptionPane.showMessageDialog(null, "Tipo de vehículo no reconocido.");
                    return;
                }

                listaInventario.add(nuevoVehiculo);
                JOptionPane.showMessageDialog(null, tipo + " registrada correctamente.");
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error en el formato de datos. Verifique los campos numéricos.");
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


    }

}
