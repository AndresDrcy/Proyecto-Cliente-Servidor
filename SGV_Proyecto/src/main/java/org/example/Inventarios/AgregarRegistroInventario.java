package org.example.Inventarios;

import org.example.Automovil;
import org.example.Motocicleta;
import org.example.Vehiculo;
import org.example.ConexionBD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class AgregarRegistroInventario extends JFrame {
    private JPanel PanelAgregar;
    private JButton agregarAlInventarioButton;
    private JTextField marcavehiculoFil;
    private JTextField modelovehiculoFil;
    private JTextField colorvehiculoFil;
    private JTextField añovehiculoFil;
    private JTextField preciovehiculoFil;
    private JComboBox listaTipojc;
    private JButton salirButton;


    public AgregarRegistroInventario() {
        setContentPane(PanelAgregar);
        setTitle("Agregar Registros Inventarios");
        setSize(400, 400);
        setVisible(true);

        agregarAlInventarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Conectamos a la base de datos
                    ConexionBD conexionBD = new ConexionBD();
                    conexionBD.setConexion();

                    String marca = marcavehiculoFil.getText();
                    String modelo = modelovehiculoFil.getText();
                    String color = colorvehiculoFil.getText();
                    int anno = Integer.parseInt(añovehiculoFil.getText());
                    double precio = Double.parseDouble(preciovehiculoFil.getText());
                    String tipo = listaTipojc.getSelectedItem().toString();
                    String codigoUnico = generarCodigoUnico();

                    String sql = "INSERT INTO at_inventarios (codigo_unico, marca, modelo, color, anno, precio, tipo, numero_puertas, tiene_aire, cilindrada) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    conexionBD.setConsulta(sql);
                    PreparedStatement conexion = conexionBD.getConsulta();

                    conexion.setString(1, codigoUnico);
                    conexion.setString(2, marca);
                    conexion.setString(3, modelo);
                    conexion.setString(4, color);
                    conexion.setInt(5, anno);
                    conexion.setDouble(6, precio);
                    conexion.setString(7, tipo);

                    if (tipo.equalsIgnoreCase("Automovil")) {
                        int numeroPuertas = Integer.parseInt(JOptionPane.showInputDialog("Número de puertas:"));
                        boolean tieneAire = JOptionPane.showConfirmDialog(null, "Tiene aire acondicionado?", "Aire Acondicionado",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

                        conexion.setInt(8, numeroPuertas);
                        conexion.setBoolean(9, tieneAire);
                        conexion.setNull(10, java.sql.Types.INTEGER);
                    } else if (tipo.equalsIgnoreCase("Motocicleta")) {
                        int cilindrada = Integer.parseInt(JOptionPane.showInputDialog("Cilindrada (cc):"));
                        conexion.setNull(8, java.sql.Types.INTEGER);
                        conexion.setNull(9, java.sql.Types.BOOLEAN);
                        conexion.setInt(10, cilindrada);
                    } else {
                        JOptionPane.showMessageDialog(null, "Tipo de vehículo no reconocido.");
                        conexionBD.cerrarConexion();
                        return;
                    }

                    conexion.executeUpdate();
                    conexionBD.cerrarConexion();

                    JOptionPane.showMessageDialog(null, tipo + " registrada correctamente en la base de datos.");
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error en el formato de datos. Verifique los campos numéricos.");
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
    }

//aqui se genera un codigo unico, usando UUID para que genere un numero random de 8 digitos todos en mayuscula
    private String generarCodigoUnico() {
        return "VH-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

}
