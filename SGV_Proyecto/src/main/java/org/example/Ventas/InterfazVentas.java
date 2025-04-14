package org.example.Ventas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazVentas extends JFrame {

    private JButton registrarVentaButton;
    private JButton verRegistroDeVentasButton;
    private JButton salirButton;
    private JPanel panelVentas;
    private JLabel TituloMenuVentas;

    public InterfazVentas() {
        setContentPane(panelVentas);
        setTitle("Interfaz Ventas");
        setSize(600, 400);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        registrarVentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistroVentas();
            }
        });

        verRegistroDeVentasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HistorialVentas().setVisible(true);
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
