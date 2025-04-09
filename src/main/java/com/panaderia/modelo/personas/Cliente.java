package com.panaderia.modelo.personas;

import com.panaderia.modelo.productos.Producto;
import com.panaderia.modelo.ventas.Venta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Cliente extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Venta> ventasRealizadas = new ArrayList<>();

    public Cliente(){

    }

    public Cliente(String nombre, String cedula) {
        super(nombre, cedula);

    }

    public List<Venta> getVentasRealizadas(){
        return ventasRealizadas;
    }

    public void setVentasRealizadas (List<Venta> ventasRealizadas){
        this.ventasRealizadas =ventasRealizadas;
    }

    public void comprarProducto(List<Producto> productos) {
        Venta venta = new Venta(new Date());
        venta.setCliente(this);
        for (Producto producto : productos) {
            venta.agregarProducto(producto);
        }
        venta.calcularTotal();
        ventasRealizadas.add(venta);
    }


}
