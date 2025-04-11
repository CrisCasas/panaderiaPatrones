package com.panaderia;

import java.util.List;

import com.panaderia.controlador.*;
import com.panaderia.dao.ProductoDAO;
import com.panaderia.modelo.productos.*;
import com.panaderia.modelo.sistema.SistemaAdministracion;

public class SistemaCache {

    private static SistemaCache instancia;

    private final SistemaAdministracion sistema;
    private final ControladorVentas ctrlVentas;
    private final ControladorInventario ctrlInventario;
    private final ControladorAdministrador ctrlAdmin;

    private SistemaCache() {
        sistema = new SistemaAdministracion();
        ctrlVentas = new ControladorVentas(sistema);
        ctrlInventario = new ControladorInventario(sistema);
        ctrlAdmin = new ControladorAdministrador(sistema);

        // 👉 Intentar cargar los productos desde el archivo binario
        List<Producto> productos = ProductoDAO.cargarProductos();

        if (productos.isEmpty()) {
            // 🚨 Si no hay productos guardados, crear productos de prueba
            System.out.println("⚠️ No se encontraron productos. Se cargarán productos de prueba.");
            ctrlInventario.agregarProducto(new com.panaderia.modelo.productos.Pan(
                    "Pan", 1000, 600, 10, new com.panaderia.modelo.productos.AdicionPan("Sin adición", 0, 0)));
            ctrlInventario.agregarProducto(new com.panaderia.modelo.productos.Galleta(
                    "Galleta", 800, 400, 13, new com.panaderia.modelo.productos.AdicionPan("Sin adición", 0, 0)));
        } else {
            // ✅ Cargar productos al inventario desde archivo
            for (Producto producto : productos) {
                ctrlInventario.agregarProducto(producto);
            }
            System.out.println("📦 Productos cargados exitosamente desde data/productos.dat");
        }
    }

    public static SistemaCache getInstance() {
        if (instancia == null) instancia = new SistemaCache();
        return instancia;
    }

    public ControladorVentas getCtrlVentas() { return ctrlVentas; }
    public ControladorInventario getCtrlInventario() { return ctrlInventario; }
    public ControladorAdministrador getCtrlAdmin() { return ctrlAdmin; }	
	
}
