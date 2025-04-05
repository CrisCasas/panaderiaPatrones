package com.panaderia.modelo.sistema;

import com.panaderia.modelo.productos.Producto;
import com.panaderia.modelo.productos.Pan;
import com.panaderia.modelo.productos.Galleta;
import com.panaderia.modelo.ventas.Venta;
import com.panaderia.util.ReporteCSV;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class SistemaAdministracion implements FabricaProducto{
    
    private List<Producto> listaProductos = new ArrayList<>();
    private List<Venta> listaVentas = new ArrayList<>();

    public List<Producto> filtrarPorNombre(String nombre) {
        return listaProductos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .collect(Collectors.toList());
    }

    public List<Producto> filtrarPorPrecio(double precio) {
        return listaProductos.stream()
                .filter(p -> p.getPrecioVenta() == precio)
                .collect(Collectors.toList());
    }

    public List<Producto> filtrarPorCantidad(int cantidad) {
        return listaProductos.stream()
                .filter(p -> p.getCantidad() == cantidad)
                .collect(Collectors.toList());
    }

    public void generarReporteCSV() {
        ReporteCSV.generar(listaVentas);
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public List<Venta> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(List<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }

    @Override
    public Producto creaProducto(String tipo) {
        if (tipo.equalsIgnoreCase("pan")) {
            return new Pan("Pan Genérico", 1000, 500, 10, false);
        } else if (tipo.equalsIgnoreCase("galleta")) {
            return new Galleta("Galleta Genérica", 800, 400, 15, false);
        }
        return null;
    }
}
