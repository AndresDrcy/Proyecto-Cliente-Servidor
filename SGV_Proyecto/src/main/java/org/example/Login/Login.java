package org.example.Login;

import org.example.ConexionBD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Login extends JFrame {
    private JPanel Login;
    private JTextField UsuarioTXT;
    private JPasswordField ContraseñaTXT;
    private JLabel UsuarioLB;
    private JLabel ContraseñaLB;
    private JButton LoginBtn;
    private JButton SalirBtn;
    private boolean autenticado = false;

    public Login() {
        setContentPane(Login);
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);

        LoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = UsuarioTXT.getText();
                String contrasena = new String(ContraseñaTXT.getPassword());

                //se aplica la revision en la BD
                if (validarCredenciales(usuario, contrasena)) {
                    JOptionPane.showMessageDialog(null, "Ingreso correcto, bienvenido.");
                    autenticado = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Verifique sus credenciales");
                    autenticado = false;
                }
            }
        });

        SalirBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public boolean esAutenticado() {
        return autenticado;
    }

    private boolean validarCredenciales(String usuario, String contrasena) {
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            String consulta = "SELECT * FROM at_usuarios WHERE usuario = ? AND contrasena = ?";
            conexion.setConsulta(consulta);
            conexion.getConsulta().setString(1, usuario);
            conexion.getConsulta().setString(2, contrasena);

            ResultSet resultadoUS = conexion.getResultado();

            if (resultadoUS.next()) {
                return true; // Usuario encontrado
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al verificar credenciales: " + ex.getMessage());
        } finally {
            conexion.cerrarConexion();
        }

        return false;
    }
}
