package org.example.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

public class AgregarCliente extends JFrame {
    private JTextField nombreTextField;
    private JTextField DNITextField;
    private JTextField edadTextField;
    private JTextField telefonoTextField;
    private JTextField correoMailComTextField;
    private JButton agregarRegistroButton;
    private JPanel panel;

    public AgregarCliente(ArrayList<Cliente> listaClientes) {


        setContentPane(panel);
        setTitle("Menu Clientes"); //Nombre de la Ventana
        setSize(400,400); //Tamaño de la ventana
        setVisible(true);

        agregarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreTextField.getText();
                String documento = DNITextField.getText();
                int edad = Integer.parseInt(edadTextField.getText());
                String telefono = telefonoTextField.getText();
                String correo = correoMailComTextField.getText();
                Cliente registroNuevo = new Cliente(nombre, documento, edad, telefono, correo);

                listaClientes.add(registroNuevo); //aqui ingresa el dato a la DB de SQL en el futuro
                JOptionPane.showMessageDialog(null,"Registro "+ nombre + " agregado a la lista de clientes");
                dispose();
            }
        });
        nombreTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                nombreTextField.setText("");
            }
        });
        DNITextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                DNITextField.setText("");
            }
        });
        edadTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                edadTextField.setText("");
            }
        });
        telefonoTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                telefonoTextField.setText("");
            }
        });
        correoMailComTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                correoMailComTextField.setText("");
            }
        });
    }
}
