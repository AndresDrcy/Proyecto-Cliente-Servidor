package org.example.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazCliente extends JFrame {

    private JPanel menuClientesPanel;
    private JLabel menuDeClientesLabel;
    private JButton agregarClienteButton;
    private JButton mostrarClientesButton;
    private JButton salirButton;

    public InterfazCliente() {
        setContentPane(menuClientesPanel);
        setTitle("Menú de Clientes");
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        agregarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarCliente();
            }
        });

        mostrarClientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MostrarCliente();
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
