package org.example.Cliente;

import javax.swing.*;
import java.util.ArrayList;

public class MostrarCliente extends JFrame {
    private JPanel mostrarPanel;

    public MostrarCliente(ArrayList<Cliente> listaClientes) {
        setContentPane(mostrarPanel);
        setTitle("Menu Clientes"); //Nombre de la Ventana
        setSize(400,400); //Tamaño de la ventana
        setVisible(true);

    }

}
