package org.example.Login;

import org.example.Cliente.InterfazCliente;
import org.example.Inventarios.InterfazInventarios;
import org.example.Inventarios.MostrarInventarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {

    private JPanel panelMenuPrincipal;
    private JButton Inventariosbtn;
    private JButton Clientesbtn;
    private JButton Ventabtn;
    private JLabel Menulbl;
    private JButton salirButton;

    public MenuPrincipal() {

        setContentPane(panelMenuPrincipal);
        setTitle("Menu Principal"); //Nombre de la Ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE); //cierre la ventana mediante alguna funcionalidad
        setSize(400,400); //Tamaño de la ventana
        setVisible(true);

    Clientesbtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            InterfazCliente interfazCliente = new InterfazCliente();
        }
    });

    Inventariosbtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            InterfazInventarios interfazInventarios = new InterfazInventarios();
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
