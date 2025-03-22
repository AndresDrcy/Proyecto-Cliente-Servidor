package org.example.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InterfazCliente extends JFrame {

    ArrayList<Cliente> listaClientes = new ArrayList<>();
    private JPanel menuClientesPanel;
    private JLabel menuDeClientesLabel;
    private JButton agregarClienteButton;
    private JButton mostrarClientesButton;
    private JButton editarClientesButton;
    private JButton salirButton;

    public InterfazCliente() {

        setContentPane(menuClientesPanel);
        setTitle("Menu Clientes"); //Nombre de la Ventana
        setSize(400,400); //Tamaño de la ventana
        setVisible(true);

        agregarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AgregarCliente menuAgregar = new AgregarCliente(listaClientes);
            }
        });
        mostrarClientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("MOSTRAR");
                MostrarCliente mostrarCliente = new MostrarCliente(listaClientes);
            }
        });
        editarClientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("EDITAR");
                EditarCliente editarCliente = new EditarCliente(listaClientes);
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
