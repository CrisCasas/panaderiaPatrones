package com.panaderia.dao;

import com.panaderia.modelo.ventas.Venta;
import java.util.List;

public class VentaDAO {
    private static final String RUTA_ARCHIVO = "data/ventas.dat";

    public static void guardarVentas(List<Venta> ventas) {
        BinarioUtil.guardarLista(ventas, RUTA_ARCHIVO);
    }

    public static List<Venta> cargarVentas() {
        return BinarioUtil.cargarLista(RUTA_ARCHIVO);
    }
}
