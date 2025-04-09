package com.panaderia.modelo.productos;

import java.util.List;

public class Pan extends Producto {
    private static final List<String> ADICIONES_VALIDAS = List.of("queso");
    private Adicion adicion;

    public Pan() {
        super(); 
    }


    public Pan(String nombre, double precioVenta, double costoProduccion, int cantidad, Adicion adicionQueso) {
        super(nombre, precioVenta, costoProduccion, cantidad);
        this.adicion=adicionQueso;
    }

    @Override
    public boolean agregarAdicion(Adicion adicion) {
        if (adicion instanceof AdicionPan && ADICIONES_VALIDAS.contains(adicion.getNombre().toLowerCase())) {
            return super.agregarAdicion(adicion);
        } else {
            System.out.println("Adición inválida para Pan.");
            return false;
        }
    }
    
    @Override
    public boolean isTieneAdicion() {
        return adicion != null && !adicion.getNombre().equalsIgnoreCase("Sin adición");
    }


    @Override
    public String toString() {
        return "Producto: " + getNombre() + 
            " | Precio: $" + getPrecioVenta() + 
            " | Costo: $" + getCostoProduccion() + 
            " | Cantidad: " + getCantidad() + 
            " | Adiciones: " + (isTieneAdicion() ? mostrarAdiciones() : "Ninguna");
    }

    private String mostrarAdiciones() {
        return getAdiciones().stream()
                .map(Adicion::getNombre)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Ninguna");
    }

}
