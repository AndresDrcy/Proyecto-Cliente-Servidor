package org.example.Inventarios;

import org.example.Vehiculo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InterfazInventarios extends JFrame{

    private ArrayList<Vehiculo> listaInventario;
    private JPanel PanelInventario;
    private JButton agregarRegistroButton;
    private JButton mostrarInventarioButton;
    private JButton editarInventarioButton;
    private JButton salirButton;


    public InterfazInventarios(){

        listaInventario = new ArrayList<>();
        setContentPane(PanelInventario);
        setTitle("Menu Clientes"); //Nombre de la Ventana
        setSize(400,400); //Tamaño de la ventana
        setVisible(true);

        mostrarInventarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MostrarInventarios mostrarInventarios = new MostrarInventarios(listaInventario);
            }
        });

        agregarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarRegistroInventario agregarRegistroInventario = new AgregarRegistroInventario(listaInventario);
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
