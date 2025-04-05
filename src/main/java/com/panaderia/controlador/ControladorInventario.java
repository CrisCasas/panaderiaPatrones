package com.panaderia.controlador;


import java.util.List;
import java.util.stream.Collectors;

import com.panaderia.modelo.productos.Producto;
import com.panaderia.modelo.sistema.SistemaAdministracion;

public class ControladorInventario {


    private SistemaAdministracion sistema;


    public ControladorInventario(SistemaAdministracion sistema){
        this.sistema=sistema;
    }

    //agregar Producto
    public void agregarProducto (Producto producto){
        sistema.getListaProductos().add(producto);
    }

    // Eliminar producto
    public boolean eliminarProducto(String nombreProducto) {
        return sistema.getListaProductos().
        removeIf(
            p -> p.getNombre().equalsIgnoreCase(nombreProducto)
            );
    }

    //Actualizar cantidad en inventario según nombreProducto
    public boolean actualizarCantidadProducto(String nombreProducto, int nuevaCantidad){
        
        for(Producto producto: sistema.getListaProductos()){
            if(producto.getNombre().equalsIgnoreCase(nombreProducto)){
                producto.setCantidad(nuevaCantidad);
                return true;
            }
        }

        return false;
    }

    // Obtener todos los productos del inventario
    public List<Producto> obtenerInventarioCompleto(){
        return sistema.getListaProductos();
    }

    // Buscar productos por nombre (o parte del nombre)
    public List<Producto> buscarProductoPorNombre(String nombre){
        return sistema.getListaProductos().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }

    //Productos con bajo stock

    public List<Producto> obtenerProductosConStockBajo(int limite) {
        return sistema.getListaProductos().stream()
                .filter(p -> p.getCantidad() <= limite)
                .collect(Collectors.toList());
    }

    //Productos por Rango Precio

    public List<Producto> buscarProductosPorRangoPrecio(double precioMin, double precioMax) {
        return sistema.getListaProductos().stream()
                .filter(p -> p.getPrecioVenta() >= precioMin && p.getPrecioVenta() <= precioMax)
                .collect(Collectors.toList());
    }
    
}
