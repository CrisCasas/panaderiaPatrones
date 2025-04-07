package com.panaderia.vista.controladoresFXML;

import com.panaderia.SistemaCache;
import com.panaderia.controlador.ControladorInventario;
import com.panaderia.modelo.productos.Producto;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class AdminGUIController {


    @FXML
    private TextArea areaInventario;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCantidad;

    private ControladorInventario ctrlInventario;

    @FXML
    public void initialize() {
        ctrlInventario = SistemaCache.getInstance().getCtrlInventario();
        mostrarInventario();
    }

    @FXML
    public void agregarInventario() {
        String nombre = txtNombre.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();

        if (nombre.isEmpty() || cantidadStr.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor, ingrese nombre y cantidad.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                mostrarAlerta(Alert.AlertType.WARNING, "Cantidad inválida", "La cantidad debe ser mayor a 0.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Formato inválido", "La cantidad debe ser un número entero.");
            return;
        }

        List<Producto> encontrados = ctrlInventario.buscarProductoPorNombre(nombre);

        if (encontrados.isEmpty()) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Producto no encontrado", "No se encontró el producto con ese nombre.");
            return;
        }

        Producto producto = encontrados.get(0); // Tomamos el primero que coincida
        int nuevaCantidad = producto.getCantidad() + cantidad;
        ctrlInventario.actualizarCantidadProducto(producto.getNombre(), nuevaCantidad);

        mostrarAlerta(Alert.AlertType.INFORMATION, "✅ Inventario actualizado", "Se agregó cantidad al producto.");
        txtNombre.clear();
        txtCantidad.clear();
        mostrarInventario(); // refresca el textArea
    }

    @FXML
    public void generarReporte() {
        // Lo completamos en otra fase
    }

    private void mostrarInventario() {
        List<Producto> inventario = ctrlInventario.obtenerInventarioCompleto();
        StringBuilder sb = new StringBuilder();
        for (Producto p : inventario) {
            sb.append(p.toString()).append("\n");
        }
        areaInventario.setText(sb.toString());
    }	
	
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
