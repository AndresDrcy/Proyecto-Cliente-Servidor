package org.example.Ventas;
import org.example.Cliente.Cliente;
import org.example.Vehiculo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InterfazVentas extends JFrame{


    private ArrayList<Venta> registroVentas;
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Vehiculo> listaInventario;

    private JButton registrarVentaButton;
    private JButton verRegistroDeVentasButton;
    private JButton salirButton;
    private JPanel panelVentas;

    public InterfazVentas(ArrayList<Venta> registroVentas, ArrayList<Cliente> listaClientes, ArrayList<Vehiculo> listaInventario) {
        this.registroVentas = registroVentas;
        this.listaClientes = listaClientes;
        this.listaInventario = listaInventario;

        setContentPane(panelVentas);
        setTitle("Interfaz Ventas");
        setSize(400, 400);
        setVisible(true);

        registrarVentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistroVentas ventaRegistro = new RegistroVentas(registroVentas, listaClientes, listaInventario);
            }
        });

        verRegistroDeVentasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lo veremos después
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
