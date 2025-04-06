package com.panaderia.vista;

import com.panaderia.controlador.ControladorInventario;
import com.panaderia.modelo.productos.Producto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class BuscarPorNombreGUI extends JFrame {
	
    public BuscarPorNombreGUI(ControladorInventario ctrlInventario) {
        setTitle("Buscar Producto por Nombre");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Nombre del producto:"));
        JTextField txtNombre = new JTextField(20);
        inputPanel.add(txtNombre);

        JTextArea resultadosArea = new JTextArea();
        resultadosArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(resultadosArea);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener((ActionEvent e) -> {
            String nombre = txtNombre.getText();
            List<Producto> encontrados = ctrlInventario.buscarProductoPorNombre(nombre);
            if (encontrados.isEmpty()) {
                resultadosArea.setText("⚠️ No se encontraron productos.");
            } else {
                StringBuilder resultado = new StringBuilder();
                encontrados.forEach(p -> resultado.append(p.toString()).append("\n"));
                resultadosArea.setText(resultado.toString());
            }
        });

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btnBuscar, BorderLayout.SOUTH);
        add(panel);
        setVisible(true);
    }
}
