package com.panaderia.modelo.ventas;

import java.util.ArrayList;
import java.util.List;

import com.panaderia.modelo.productos.Producto;

public class Inventario {
    
    private List<Producto> productos = new ArrayList<>();


    public List<Producto> listarProductos() {
        return productos;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
