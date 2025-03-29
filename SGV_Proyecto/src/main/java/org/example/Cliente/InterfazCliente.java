package org.example.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazCliente extends JFrame {

    private JPanel menuClientesPanel;
    private JLabel menuDeClientesLabel;
    private JButton agregarClienteButton;
    private JButton mostrarClientesButton;
    private JButton editarClientesButton;
    private JButton salirButton;

    public InterfazCliente() {

        setContentPane(menuClientesPanel);
        setTitle("Menú Clientes");
        setSize(400, 400);
        setVisible(true);

        agregarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarCliente menuAgregar = new AgregarCliente();
            }
        });

        mostrarClientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MostrarCliente mostrarCliente = new MostrarCliente(); // ← Esta clase debe leer directamente desde SQL
            }
        });

        editarClientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditarCliente editarCliente = new EditarCliente(); // ← Y esta también
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
