package com.panaderia.modelo.ventas;

import com.panaderia.modelo.personas.Cliente;
import com.panaderia.modelo.productos.Producto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Venta implements Serializable{

    private Cliente cliente;
    private Date fecha;
    private double totalVenta;
    private List<Producto> productosVendidos = new ArrayList<>();
    private static final long serialVersionUID = 1L;

    public Venta(Date fecha) {
        this.fecha = fecha;
    }

    public void agregarProducto(Producto producto) {
        productosVendidos.add(producto);
    }

    public void removerProducto(String nombreProducto) {
        productosVendidos.removeIf(p -> p.getNombre().equals(nombreProducto));
    }

    public void calcularTotal() {
        totalVenta = productosVendidos.stream().mapToDouble(p -> {
            double total = p.getPrecioVenta();
            total += p.getAdiciones().stream().mapToDouble(a -> a.getCosto()).sum();
            return total;
        }).sum();
    }
    

    public List<Producto> listarProductosVendidos() {
        return productosVendidos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public List<Producto> getProductosVendidos() {
        return productosVendidos;
    }

    public void setProductosVendidos(List<Producto> productosVendidos) {
        this.productosVendidos = productosVendidos;
    }

    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        StringBuilder productos = new StringBuilder();
        for (Producto producto : productosVendidos) {
            productos.append(producto.getNombre()).append("|");
        }
    
        // Quitar el último "|", si hay productos
        if (productos.length() > 0) {
            productos.setLength(productos.length() - 1);
        }
    
        String nombreCliente = (cliente != null) ? cliente.getNombre() : "Sin cliente";
    
        return String.format("%s,%tF,%.2f,%s", nombreCliente, fecha, totalVenta, productos.toString());
    }
    

}
