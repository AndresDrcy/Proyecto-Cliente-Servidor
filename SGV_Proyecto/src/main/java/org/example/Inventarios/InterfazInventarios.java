package org.example.Inventarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazInventarios extends JFrame {

    private JPanel PanelInventario;
    private JButton agregarRegistroButton;
    private JButton mostrarInventarioButton;
    private JButton salirButton;

    public InterfazInventarios() {
        setContentPane(PanelInventario);
        setTitle("Menú Inventarios");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        mostrarInventarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MostrarInventarios mostrarInventarios = new MostrarInventarios();
            }
        });

        agregarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarRegistroInventario agregarRegistroInventario = new AgregarRegistroInventario();
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

