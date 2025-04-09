package com.panaderia.modelo.productos;


public class AdicionGalleta extends Adicion {
    public AdicionGalleta(String nombre, double precioVenta, double costoProduccion) {
        super(nombre, precioVenta, costoProduccion);
    }

    @Override
    public String tipo() {
        return "Galleta";
    }
}

