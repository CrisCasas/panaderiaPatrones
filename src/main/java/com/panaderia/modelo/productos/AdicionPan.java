package com.panaderia.modelo.productos;


public class AdicionPan extends Adicion {
    public AdicionPan(String nombre, double precioVenta, double costoProduccion) {
        super(nombre, precioVenta, costoProduccion);
    }

    @Override
    public String tipo() {
        return "Pan";
    }
}

