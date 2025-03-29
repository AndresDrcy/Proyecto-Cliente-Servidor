package org.example.Cliente;

import org.example.ConexionBD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.PreparedStatement;

public class AgregarCliente extends JFrame {
    private JTextField nombreTextField;
    private JTextField DNITextField;
    private JTextField edadTextField;
    private JTextField telefonoTextField;
    private JTextField correoMailComTextField;
    private JButton agregarRegistroButton;
    private JPanel panel;

    public AgregarCliente() {
        setContentPane(panel);
        setTitle("Menu Clientes");
        setSize(400, 400);
        setVisible(true);

        agregarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreTextField.getText();
                String documento = DNITextField.getText();
                int edad = Integer.parseInt(edadTextField.getText());
                String telefono = telefonoTextField.getText();
                String correo = correoMailComTextField.getText();

                try {
                    ConexionBD conexionBD = new ConexionBD();
                    conexionBD.setConexion();

                    String sql = "INSERT INTO at_cliente (documento_identidad, nombre, edad, telefono, correo) VALUES (?, ?, ?, ?, ?)";
                    conexionBD.setConsulta(sql);
                    PreparedStatement consulta = conexionBD.getConsulta();

                    consulta.setString(1, documento);
                    consulta.setString(2, nombre);
                    consulta.setInt(3, edad);
                    consulta.setString(4, telefono);
                    consulta.setString(5, correo);

                    consulta.executeUpdate();
                    conexionBD.cerrarConexion();

                    JOptionPane.showMessageDialog(null, "cliente " + nombre + " registrado en la base de datos.");
                    dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "error al registrar cliente: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        // Limpieza de campos al hacer foco
        nombreTextField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { nombreTextField.setText(""); }
        });
        DNITextField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { DNITextField.setText(""); }
        });
        edadTextField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { edadTextField.setText(""); }
        });
        telefonoTextField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { telefonoTextField.setText(""); }
        });
        correoMailComTextField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { correoMailComTextField.setText(""); }
        });
    }
}
