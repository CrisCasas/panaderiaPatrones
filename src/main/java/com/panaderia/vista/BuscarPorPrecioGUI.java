package com.panaderia.vista;

import com.panaderia.controlador.ControladorInventario;
import com.panaderia.modelo.productos.Producto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class BuscarPorPrecioGUI extends JFrame {
	
    public BuscarPorPrecioGUI(ControladorInventario ctrlInventario) {
        setTitle("Buscar Producto por Rango de Precio");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Precio mínimo:"));
        JTextField txtMin = new JTextField(5);
        inputPanel.add(txtMin);

        inputPanel.add(new JLabel("Precio máximo:"));
        JTextField txtMax = new JTextField(5);
        inputPanel.add(txtMax);

        JTextArea resultadosArea = new JTextArea();
        resultadosArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(resultadosArea);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener((ActionEvent e) -> {
            try {
                double min = Double.parseDouble(txtMin.getText());
                double max = Double.parseDouble(txtMax.getText());
                List<Producto> encontrados = ctrlInventario.buscarProductosPorRangoPrecio(min, max);
                if (encontrados.isEmpty()) {
                    resultadosArea.setText("⚠️ No se encontraron productos.");
                } else {
                    StringBuilder resultado = new StringBuilder();
                    encontrados.forEach(p -> resultado.append(p.toString()).append("\n"));
                    resultadosArea.setText(resultado.toString());
                }
            } catch (NumberFormatException ex) {
                resultadosArea.setText("❌ Ingrese valores numéricos válidos.");
            }
        });

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btnBuscar, BorderLayout.SOUTH);
        add(panel);
        setVisible(true);
    }
}
