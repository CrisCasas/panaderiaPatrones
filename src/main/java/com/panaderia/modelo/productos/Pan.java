package com.panaderia.modelo.productos;

import java.util.List;

public class Pan extends Producto{

    private boolean adicionPan;
    private static final List<String> ADICIONES_VALIDAS = List.of("queso");

    public Pan() {
        super(); 
    }

    public Pan(String nombre, double precioVenta, double costoProduccion, int cantidad, boolean adicionPan) {
        super(nombre, precioVenta, costoProduccion, cantidad);
        this.adicionPan = adicionPan;
    }

    @Override
    public void agregarAdicion(Adicion adicion) {
        if (adicion instanceof AdicionPan && ADICIONES_VALIDAS.contains(adicion.getNombre().toLowerCase())) {
            super.agregarAdicion(adicion);
        } else {
            System.out.println("Adición inválida para Pan.");
        }
    }

    public boolean isTieneQueso() {
        return adicionPan;
    }

    public void setTieneQueso(boolean adicionPan) {
        this.adicionPan = adicionPan;
    }

    @Override
    public String toString() {
        return "Pan: " + getNombre() + 
            " | Precio: $" + getPrecioVenta() + 
            " | Costo: $" + getCostoProduccion() + 
            " | Cantidad: " + getCantidad() + 
            " | Adición: " + (adicionPan ? "Sí" : "No");
    }
    


}