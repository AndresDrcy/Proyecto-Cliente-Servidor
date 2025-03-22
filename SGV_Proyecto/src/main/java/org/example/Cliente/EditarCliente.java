package org.example.Cliente;

import javax.swing.*;
import java.util.ArrayList;

public class EditarCliente extends JFrame{
    private JPanel editarPanel;

    public EditarCliente(ArrayList<Cliente> listaClientes) {
        setContentPane(editarPanel);
        setTitle("Menu Clientes"); //Nombre de la Ventana
        setSize(400,400); //Tamaño de la ventana
        setVisible(true);
    }
}
