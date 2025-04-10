package com.panaderia.vista.controladoresFXML;

import com.panaderia.SistemaCache;
import com.panaderia.controlador.ControladorInventario;
import com.panaderia.controlador.ControladorVentas;
import com.panaderia.modelo.productos.Producto;
import com.panaderia.modelo.ventas.Venta;
import com.panaderia.vista.ReporteCSV;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AdminGUIController {

    @FXML private TextArea areaInventario;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCantidad;
    @FXML private ComboBox<String> comboArchivosDAT;
    @FXML private ComboBox<String> comboArchivosCSV;


    private ControladorInventario ctrlInventario;
    private ControladorVentas ctrlVentas; // 🆕 Nueva referencia

    @FXML
    public void initialize() {
        ctrlInventario = SistemaCache.getInstance().getCtrlInventario();
        ctrlVentas = SistemaCache.getInstance().getCtrlVentas(); // ✅ lo obtenemos desde el cache
        mostrarInventario();
        cargarArchivosDAT();
        cargarArchivosCSV();
        comboArchivosCSV.setOnShowing(e -> {
            comboArchivosCSV.getItems().clear(); // limpia lo anterior
            cargarArchivosCSV(); // recarga el contenido
        });
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
        List<Venta> ventas = ctrlVentas.obtenerListaVentas(); // ✅ accedemos correctamente a las ventas
        ReporteCSV.generar(ventas);
        mostrarAlerta(Alert.AlertType.INFORMATION, "✅ Reporte generado", "El reporte CSV fue creado exitosamente.");
    }
    
    @FXML
    public void convertirArchivoBinarioIndividual() {
        String ruta = comboArchivosDAT.getValue();
        if (ruta == null || ruta.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Archivo no seleccionado", "Selecciona un archivo .dat para convertir.");
            return;
        }

        ReporteCSV.generarDesdeArchivoEspecifico(ruta);
        mostrarAlerta(Alert.AlertType.INFORMATION, "✅ Conversión completada", "El archivo fue convertido a CSV correctamente.");

        // 🆕 Refrescamos la lista de CSVs porque hay uno nuevo
        comboArchivosCSV.getItems().clear();
        cargarArchivosCSV();
    }    
    
    @FXML
    public void abrirArchivoCSV() {
        String ruta = comboArchivosCSV.getValue();
        if (ruta == null || ruta.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Archivo no seleccionado", "Por favor selecciona un archivo CSV.");
            return;
        }

        try {
            File archivo = new File(ruta);

            if (!archivo.exists()) {
                mostrarAlerta(Alert.AlertType.ERROR, "Archivo no encontrado", "El archivo seleccionado no existe.");
                return;
            }

            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "start", "\"\"", archivo.getAbsolutePath()).start();
            } else if (os.contains("linux")) {
                new ProcessBuilder("libreoffice", "--calc", archivo.getAbsolutePath()).start();
            } else if (os.contains("mac")) {
                new ProcessBuilder("open", archivo.getAbsolutePath()).start();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Sistema operativo no compatible", "No se puede abrir el archivo automáticamente en este sistema.");
            }

        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al abrir archivo", "No se pudo abrir el archivo. Verifica que tengas un programa instalado para abrir CSV.");
            e.printStackTrace();
        }
    }    
    
    private void mostrarInventario() {
        List<Producto> inventario = ctrlInventario.obtenerInventarioCompleto();
        StringBuilder sb = new StringBuilder();
        for (Producto p : inventario) {
            sb.append(p.toString()).append("\n");
        }
        areaInventario.setText(sb.toString());
    }

    private void cargarArchivosDAT() {
        File carpeta = new File("data");
        if (!carpeta.exists()) return;

        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".dat"));
        if (archivos != null) {
            comboArchivosDAT.getItems().clear(); // 👈 Limpia antes de volver a llenar
            for (File archivo : archivos) {
                comboArchivosDAT.getItems().add(archivo.getAbsolutePath());
            }
        }
    }
        
    private void cargarArchivosCSV() {
        File carpeta = new File("data");
        if (!carpeta.exists()) return;

        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".csv"));
        if (archivos != null) {
            for (File archivo : archivos) {
                comboArchivosCSV.getItems().add(archivo.getAbsolutePath());
            }
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
