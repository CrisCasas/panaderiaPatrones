package com.panaderia.vista.controladoresFXML;

import com.panaderia.SistemaCache;
import com.panaderia.controlador.ControladorInventario;
import com.panaderia.controlador.ControladorVentas;
import com.panaderia.modelo.personas.Cliente;
import com.panaderia.modelo.productos.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class CompraProductosController {

    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colCantidad;
    @FXML private Label labelMensaje;

    private final ObservableList<Producto> productosFX = FXCollections.observableArrayList();
    private final List<Producto> carrito = new ArrayList<>();

    // Simulamos la instancia de controladores
    private final SistemaCache cache = SistemaCache.getInstance();
    private final ControladorInventario ctrlInventario = cache.getCtrlInventario();
    private final ControladorVentas ctrlVentas = cache.getCtrlVentas();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getNombre()));
        colPrecio.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getPrecioVenta()));
        colCantidad.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getCantidad()));

        productosFX.setAll(ctrlInventario.obtenerInventarioCompleto());
        tablaProductos.setItems(productosFX);
    }

    @FXML
    public void agregarAlCarrito() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            carrito.add(seleccionado);
            labelMensaje.setText("✅ Producto añadido al carrito.");
        } else {
            labelMensaje.setText("⚠️ Selecciona un producto.");
        }
    }

    @FXML
    public void agregarAdicion() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado instanceof Pan) {
            ((Pan) seleccionado).agregarAdicion(new AdicionPan("queso", 1000,600));
            labelMensaje.setText("🧀 Queso añadido al pan.");
        } else if (seleccionado instanceof Galleta) {
            ((Galleta) seleccionado).agregarAdicion(new AdicionGalleta("chispas de chocolate", 5000,1000));
            labelMensaje.setText("🍫 Chispas añadidas a la galleta.");
        } else {
            labelMensaje.setText("⚠️ El producto no admite adiciones.");
        }
    }

    @FXML
    public void finalizarCompra() {
        Cliente cliente = new Cliente("Cliente Genérico", "0000000"); // Para pruebas
        boolean exito = ctrlVentas.registrarVenta(cliente, carrito);

        if (exito) {
            labelMensaje.setText("🎉 Compra realizada con éxito.");
            carrito.clear();
        } else {
            labelMensaje.setText("❌ No se pudo completar la compra.");
        }
    }
	
}
