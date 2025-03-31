package org.example.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AgregarCliente extends JFrame {
    private JTextField nombreTextField;
    private JTextField DNITextField;
    private JTextField edadTextField;
    private JTextField telefonoTextField;
    private JTextField correoMailComTextField;
    private JButton agregarRegistroButton;
    private JPanel panel;
    private JButton salirButton;

    public AgregarCliente() {
        setContentPane(panel);
        setTitle("Agregar Cliente");
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        agregarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = nombreTextField.getText();
                    String documento = DNITextField.getText();
                    int edad = Integer.parseInt(edadTextField.getText());
                    String telefono = telefonoTextField.getText();
                    String correo = correoMailComTextField.getText();

                    Cliente nuevoCliente = new Cliente(nombre, documento, edad, telefono, correo);

                    if (nuevoCliente.registrarCliente()) {
                        JOptionPane.showMessageDialog(null, "Cliente " + nombre + " registrado correctamente");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar cliente");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Verifique los campos numéricos");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage());
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

        // Limpiar los campos al enfocar
        nombreTextField.addFocusListener(new ClearFieldOnFocus(nombreTextField));
        DNITextField.addFocusListener(new ClearFieldOnFocus(DNITextField));
        edadTextField.addFocusListener(new ClearFieldOnFocus(edadTextField));
        telefonoTextField.addFocusListener(new ClearFieldOnFocus(telefonoTextField));
        correoMailComTextField.addFocusListener(new ClearFieldOnFocus(correoMailComTextField));
    }

    private static class ClearFieldOnFocus extends FocusAdapter {
        private final JTextField textField;

        public ClearFieldOnFocus(JTextField textField) {
            this.textField = textField;
        }

        @Override
        public void focusGained(FocusEvent e) {
            textField.setText("");
        }
    }
}
