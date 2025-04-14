package org.example.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroUsuario extends JFrame {
    private JTextField UsuarioTxt;
    private JTextField ContraseñaTxt;
    private JButton agregarUsuarioButton;
    private JButton salirButton;
    private JComboBox RolCob;
    private JButton listaDeUsuariosButton;
    private JPanel panelReg;

    public RegistroUsuario (){

        setContentPane(panelReg);
        setTitle("Registro de usuarios");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        agregarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String usuarioReg = UsuarioTxt.getText();
                    String contrasenaReg = ContraseñaTxt.getText();
                    String rolReg = RolCob.getSelectedItem().toString();

                    Usuario usuario = new Usuario(usuarioReg,contrasenaReg,rolReg);
                    usuario.guardarEnBaseDeDatos();

                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    ex.printStackTrace();
                }

            }
        });

        listaDeUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MostrarUsuarios mostrarUsuarios = new MostrarUsuarios();
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
