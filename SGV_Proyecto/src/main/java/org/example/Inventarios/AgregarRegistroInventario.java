package org.example.Inventarios;

import org.example.Automovil;
import org.example.Motocicleta;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AgregarRegistroInventario extends JFrame {
    private JPanel PanelAgregar;
    private JButton agregarAlInventarioButton;
    private JTextField marcavehiculoFil;
    private JTextField modelovehiculoFil;
    private JTextField colorvehiculoFil;
    private JTextField añovehiculoFil;
    private JTextField preciovehiculoFil;
    private JComboBox listaTipojc;
    private JButton salirButton;


    public AgregarRegistroInventario() {
        setContentPane(PanelAgregar);
        setTitle("Agregar Registros Inventarios");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        agregarAlInventarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String marca = marcavehiculoFil.getText();
                    String modelo = modelovehiculoFil.getText();
                    String color = colorvehiculoFil.getText();
                    int anno = Integer.parseInt(añovehiculoFil.getText());
                    double precio = Double.parseDouble(preciovehiculoFil.getText());
                    String tipo = listaTipojc.getSelectedItem().toString();

                    if (tipo.equalsIgnoreCase("Automovil")) {
                        int numeroPuertas = Integer.parseInt(JOptionPane.showInputDialog("Número de puertas:"));
                        boolean tieneAire = JOptionPane.showConfirmDialog(null,
                                "Tiene aire acondicionado?", "Aire Acondicionado",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

                        Automovil auto = new Automovil(marca, modelo, color, anno, precio, numeroPuertas, tieneAire, tipo);
                        auto.guardarEnBaseDeDatos();

                    } else if (tipo.equalsIgnoreCase("Motocicleta")) {
                        int cilindrada = Integer.parseInt(JOptionPane.showInputDialog("Cilindrada (cc):"));

                        Motocicleta moto = new Motocicleta(marca, modelo, color, anno, precio, cilindrada, tipo);
                        moto.guardarEnBaseDeDatos();

                    } else {
                        JOptionPane.showMessageDialog(null, "Tipo de vehículo no reconocido");
                        return;
                    }

                    JOptionPane.showMessageDialog(null, tipo + "registrado correctamente en la base de datos");
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error en el formato de registros. Verifique los campos numéricos");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    ex.printStackTrace();
                }
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
