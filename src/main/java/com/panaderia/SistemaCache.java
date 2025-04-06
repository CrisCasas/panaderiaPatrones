package com.panaderia;

import com.panaderia.controlador.*;
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

        // Productos de prueba
        ctrlInventario.agregarProducto(new Pan("panBase", 1000, 600, 10, false));
        ctrlInventario.agregarProducto(new Galleta("galletaBase", 800, 400, 15, false));
    }

    public static SistemaCache getInstance() {
        if (instancia == null) instancia = new SistemaCache();
        return instancia;
    }

    public ControladorVentas getCtrlVentas() { return ctrlVentas; }
    public ControladorInventario getCtrlInventario() { return ctrlInventario; }
    public ControladorAdministrador getCtrlAdmin() { return ctrlAdmin; }	
	
}
