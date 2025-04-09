package com.panaderia.controlador;

import com.panaderia.modelo.personas.Cliente;
import com.panaderia.modelo.productos.Producto;
import com.panaderia.modelo.sistema.SistemaAdministracion;
import com.panaderia.modelo.ventas.Venta;
import com.panaderia.dao.VentaDAO; 

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;


public class ControladorVentas {
    
    private SistemaAdministracion sistema;

    public ControladorVentas(SistemaAdministracion sistema) {
        this.sistema = sistema;
    }


    public boolean registrarVenta(Cliente cliente, List<Producto> productos) {
        if (!hayStockDisponible(productos)) {
            System.out.println("Uno o más productos no tienen stock disponible.");
            return false;
        }
    
        cliente.comprarProducto(productos);
        descontarStock(productos);
    
        Venta ultimaVenta = cliente.getVentasRealizadas().get(cliente.getVentasRealizadas().size() - 1);
        sistema.getListaVentas().add(ultimaVenta);
    
        // 🧠 Persistencia binaria
        VentaDAO.guardarVentas(sistema.getListaVentas());
    
        return true;
    }

    
    private boolean hayStockDisponible(List<Producto> productos) {
        for (Producto producto : productos) {
            if (producto.getCantidad() <= 0) {
                return false;
            }
        }
        return true;
    }

    private void descontarStock(List<Producto> productos) {
        for (Producto producto : productos) {
            producto.reducirStock();
        }
    }

    public List<Venta> obtenerVentasPorCliente(Cliente cliente) {
        return cliente.getVentasRealizadas();
    }

    /**
     * @param fecha
     * @return
     */
    public List<Venta> obtenerVentasPorFecha(Date fecha){
        return sistema.getListaVentas().stream()
        .filter(venta->venta.getFecha().equals(fecha)).collect(Collectors.toList());
    }
    
    public double calcularTotalVentas(Date inicio,Date fin){
        return sistema.getListaVentas().stream()
        .filter(ventas-> !ventas.getFecha().before(inicio) && !ventas.getFecha().after(fin))
        .mapToDouble(Venta::getTotalVenta)
        .sum();
    }
    
    // ✅ NUEVO MÉTODO: Obtener todas las ventas
    public List<Venta> obtenerListaVentas() {
        return sistema.getListaVentas();
    }
}
