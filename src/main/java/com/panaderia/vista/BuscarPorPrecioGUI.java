package com.panaderia.vista;

import com.panaderia.SistemaCache;
import com.panaderia.controlador.ControladorInventario;
import com.panaderia.modelo.productos.Producto;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class BuscarPorPrecioGUI {

    public BuscarPorPrecioGUI() {
        Stage stage = new Stage();
        stage.setTitle("💲 Buscar Producto por Rango de Precio");

        // Campos para ingresar el rango
        TextField campoMin = new TextField();
        campoMin.setPromptText("Precio mínimo");

        TextField campoMax = new TextField();
        campoMax.setPromptText("Precio máximo");

        TextArea resultado = new TextArea();
        resultado.setEditable(false);

        Button btnBuscar = new Button("Buscar");

        // Acceso al controlador desde SistemaCache
        ControladorInventario ctrlInventario = SistemaCache.getInstance().getCtrlInventario();

        btnBuscar.setOnAction(e -> {
            try {
                double min = Double.parseDouble(campoMin.getText());
                double max = Double.parseDouble(campoMax.getText());

                List<Producto> resultados = ctrlInventario.buscarProductosPorRangoPrecio(min, max);

                if (resultados.isEmpty()) {
                    resultado.setText("⚠️ No se encontraron productos.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (Producto p : resultados) {
                        sb.append(p.toString()).append("\n");
                    }
                    resultado.setText(sb.toString());
                }
            } catch (NumberFormatException ex) {
                resultado.setText("❌ Ingrese valores numéricos válidos.");
            }
        });

        // Layout con espaciado y padding
        VBox layout = new VBox(10, campoMin, campoMax, btnBuscar, resultado);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 400, 320);
        stage.setScene(scene);
        stage.show();
    }
}