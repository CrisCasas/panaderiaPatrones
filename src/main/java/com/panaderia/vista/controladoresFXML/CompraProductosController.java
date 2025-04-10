package com.panaderia.vista.controladoresFXML;

import com.panaderia.SistemaCache;
import com.panaderia.controlador.ControladorInventario;
import com.panaderia.controlador.ControladorVentas;
import com.panaderia.modelo.personas.Cliente;
import com.panaderia.modelo.productos.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.*;

public class CompraProductosController {

    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colCantidad;

    @FXML private TableView<Map.Entry<String, Integer>> tablaCarrito;
    @FXML private TableColumn<Map.Entry<String, Integer>, String> colCarritoNombre;
    @FXML private TableColumn<Map.Entry<String, Integer>, Integer> colCarritoCantidad;

    @FXML private Label labelMensaje;

    private final ObservableList<Producto> productosFX = FXCollections.observableArrayList();
    private final Map<String, Integer> carrito = new HashMap<>();
    private final ObservableList<Map.Entry<String, Integer>> carritoFX = FXCollections.observableArrayList();

    private final SistemaCache cache = SistemaCache.getInstance();
    private final ControladorInventario ctrlInventario = cache.getCtrlInventario();
    private final ControladorVentas ctrlVentas = cache.getCtrlVentas();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        colPrecio.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPrecioVenta()));
        colCantidad.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getCantidad()));

        colCarritoNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getKey()));
        colCarritoCantidad.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getValue()).asObject());

        productosFX.setAll(ctrlInventario.obtenerInventarioCompleto());
        tablaProductos.setItems(productosFX);
        tablaCarrito.setItems(carritoFX);
    }

    @FXML
    public void agregarAlCarrito() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            carrito.put(seleccionado.getNombre(), carrito.getOrDefault(seleccionado.getNombre(), 0) + 1);
            actualizarTablaCarrito();
            labelMensaje.setText("✅ Producto añadido al carrito.");
        } else {
            labelMensaje.setText("⚠️ Selecciona un producto.");
        }
    }

    @FXML
    public void eliminarDelCarrito() {
        Map.Entry<String, Integer> seleccionado = tablaCarrito.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            String producto = seleccionado.getKey();
            int cantidad = carrito.get(producto);
            if (cantidad <= 1) {
                carrito.remove(producto);
            } else {
                carrito.put(producto, cantidad - 1);
            }
            actualizarTablaCarrito();
            labelMensaje.setText("❌ Producto eliminado del carrito.");
        } else {
            labelMensaje.setText("⚠️ Selecciona un producto del carrito.");
        }
    }

    @FXML
    public void agregarAdicion() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado instanceof Pan) {
            ((Pan) seleccionado).agregarAdicion(new AdicionPan("queso", 1000, 600));
            labelMensaje.setText("🧀 Queso añadido al pan.");
        } else if (seleccionado instanceof Galleta) {
            ((Galleta) seleccionado).agregarAdicion(new AdicionGalleta("chispas", 5000, 1000));
            labelMensaje.setText("🍫 Chispas añadidas a la galleta.");
        } else {
            labelMensaje.setText("⚠️ El producto no admite adiciones.");
        }
    }

    @FXML
    public void finalizarCompra() {
        List<Producto> productosVenta = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : carrito.entrySet()) {
            Producto prod = ctrlInventario.buscarProductoPorNombre(entry.getKey()).get(0);
            for (int i = 0; i < entry.getValue(); i++) {
                productosVenta.add(prod);
            }
        }

        Cliente cliente = new Cliente("Cliente Genérico", "0000000");
        boolean exito = ctrlVentas.registrarVenta(cliente, productosVenta);

        if (exito) {
            carrito.clear();
            actualizarTablaCarrito();
            actualizarInventario();
            labelMensaje.setText("🎉 Compra realizada con éxito.");
        } else {
            labelMensaje.setText("❌ No se pudo completar la compra.");
        }
    }

    private void actualizarTablaCarrito() {
        carritoFX.setAll(carrito.entrySet());
    }

    private void actualizarInventario() {
        productosFX.setAll(ctrlInventario.obtenerInventarioCompleto());
    }
}

