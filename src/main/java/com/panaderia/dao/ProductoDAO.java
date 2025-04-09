package com.panaderia.dao;

import com.panaderia.modelo.productos.Producto;
import java.util.List;

public class ProductoDAO {
    private static final String RUTA_ARCHIVO = "data/productos.dat";

    public static void guardarProductos(List<Producto> productos) {
        BinarioUtil.guardarLista(productos, RUTA_ARCHIVO);
    }

    public static List<Producto> cargarProductos() {
        return BinarioUtil.cargarLista(RUTA_ARCHIVO);
    }
}
