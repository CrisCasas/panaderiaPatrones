package com.panaderia.modelo.sistema;

import com.panaderia.modelo.productos.Producto;

public interface FabricaProducto {

    Producto creaProducto (String tipo);
}
