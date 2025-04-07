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

public class BuscarPorNombreGUI {

    public BuscarPorNombreGUI() {
        Stage stage = new Stage();
        stage.setTitle("🔍 Buscar Producto por Nombre");

        TextField campoNombre = new TextField();
        campoNombre.setPromptText("Ingrese nombre del producto");

        TextArea resultado = new TextArea();
        resultado.setEditable(false);

        Button btnBuscar = new Button("Buscar");

        // Instanciar tu backend de control
        ControladorInventario ctrlInventario = SistemaCache.getInstance().getCtrlInventario();

        btnBuscar.setOnAction(e -> {
            try {
                String nombreBuscado = campoNombre.getText().trim();

                if (nombreBuscado.isEmpty()) {
                    resultado.setText("⚠️ El campo de nombre no puede estar vacío.");
                    return;
                }

                List<Producto> resultados = ctrlInventario.buscarProductoPorNombre(nombreBuscado);

                if (resultados.isEmpty()) {
                    resultado.setText("⚠️ No se encontraron productos con ese nombre.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (Producto p : resultados) {
                        sb.append(p.toString()).append("\n");
                    }
                    resultado.setText(sb.toString());
                }

            } catch (Exception ex) {
                resultado.setText("❌ Ocurrió un error al buscar el producto.\nDetalles: " + ex.getMessage());
                ex.printStackTrace(); // Opcional: para depuración en consola
            }
        });

        VBox layout = new VBox(10, campoNombre, btnBuscar, resultado);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}