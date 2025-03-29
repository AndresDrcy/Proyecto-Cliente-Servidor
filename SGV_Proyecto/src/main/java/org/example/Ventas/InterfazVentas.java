package org.example.Ventas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazVentas extends JFrame {

    private JButton registrarVentaButton;
    private JButton verRegistroDeVentasButton;
    private JButton salirButton;
    private JPanel panelVentas;

    public InterfazVentas() {
        setContentPane(panelVentas);
        setTitle("Interfaz Ventas");
        setSize(400, 400);
        setVisible(true);

        registrarVentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistroVentas ventaRegistro = new RegistroVentas(); // ← ya no necesita listas
            }
        });

        /*verRegistroDeVentasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MostrarVentas mostrarVentas = new MostrarVentas(); // ← clase futura para mostrar el registro desde SQL
            }
        });*/

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
