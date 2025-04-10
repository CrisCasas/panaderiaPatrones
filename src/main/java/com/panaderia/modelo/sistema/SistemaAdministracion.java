package com.panaderia.modelo.sistema;

import com.panaderia.modelo.productos.Producto;
import com.panaderia.modelo.productos.Pan;
import com.panaderia.modelo.productos.Adicion;
import com.panaderia.modelo.productos.AdicionGalleta;
import com.panaderia.modelo.productos.AdicionPan;
import com.panaderia.modelo.productos.Galleta;
import com.panaderia.modelo.ventas.Venta;
import com.panaderia.vista.ReporteCSV;
import com.panaderia.modelo.personas.Cliente;
import com.panaderia.modelo.personas.Administrador;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SistemaAdministracion implements FabricaProducto {

    private List<Producto> listaProductos = new ArrayList<>();
    private List<Venta> listaVentas = new ArrayList<>();
    private List<Cliente> listaClientes = new ArrayList<>();
    private List<Administrador> listaAdministradores = new ArrayList<>();

    // Constructor para inicializar con datos persistidos
    public SistemaAdministracion(List<Producto> productos, List<Venta> ventas, List<Cliente> clientes, List<Administrador> administradores) {
        this.listaProductos = productos != null ? productos : new ArrayList<>();
        this.listaVentas = ventas != null ? ventas : new ArrayList<>();
        this.listaClientes = clientes != null ? clientes : new ArrayList<>();
        this.listaAdministradores = administradores != null ? administradores : new ArrayList<>();
    }

    //Constructor vacío (por compatibilidad)
    public SistemaAdministracion() {}


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

    // Getters y setters

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

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public List<Administrador> getListaAdministradores() {
        return listaAdministradores;
    }

    public void setListaAdministradores(List<Administrador> listaAdministradores) {
        this.listaAdministradores = listaAdministradores;
    }

    @Override
    public Producto creaProducto(String tipo) {
        if (tipo.equalsIgnoreCase("pan")) {
            // Crear adición por defecto para el pan
            Adicion adicionPorDefecto = new AdicionPan("Sin adición", 0.0, 0.0);
            Pan pan = new Pan("Pan Genérico", 1000, 500, 10, adicionPorDefecto);
            pan.setAdiciones(new ArrayList<>()); // Si manejas lista adicional
            return pan;
        } else if (tipo.equalsIgnoreCase("galleta")) {
            // Crear adición por defecto para la galleta
            Adicion adicionPorDefecto = new AdicionGalleta("Sin adición", 0.0, 0.0);
            Galleta galleta = new Galleta("Galleta Genérica", 800, 400, 15, adicionPorDefecto);
            galleta.setAdiciones(new ArrayList<>()); // Si manejas lista adicional
            return galleta;
        }
        return null;
    }

    
    
}
