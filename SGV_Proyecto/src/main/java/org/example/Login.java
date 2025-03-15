package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JPanel Login;
    private JTextField UsuarioTXT;
    private JPasswordField ContraseñaTXT;
    private JLabel UsuarioLB;
    private JLabel ContraseñaLB;
    private JButton LoginBtn;
    private JButton SalirBtn;
    private boolean autenticado = false;

    //1- constructor inicializa la clase
    public Login(){
        //Los siguientes metodos vienen de Jframe, no hay que incializar, son heredados

        setContentPane(Login); //Main panel del Java Swing, contenedor
        setTitle("Login"); //Nombre de la Ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE); //cierre la ventana mediante alguna funcionalidad
        setSize(400,400); //Tamaño de la ventana
        setVisible(true); //ventana activa o inactiva

        //PRIMER EVENTO click en login botton (con usuario y contraseña)
        LoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Aca va_todo lo que se ejecuta a la hora del click
                String user = UsuarioTXT.getText();
                String password = ContraseñaTXT.getText();

                //Validacion del login
                if(user.equals("ventas1")&& password.equals("1234")){
                    JOptionPane.showMessageDialog(null,"Ingreso Correcto, Bienvenido!");
                    autenticado = true;
                    //cerramos la ventana del menu
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null,"Verifique sus Credenciales");
                    autenticado = false;
                }
            }
        });

        //SEGUNDO EVENTO Salir de la interfaz
        SalirBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }
    // Método_para obtener el estado de autenticación
    public boolean esAutenticado() {
        return autenticado;
    }

}
