package com.panaderia.modelo.productos;

import java.util.List;

public class Galleta extends Producto{

    private static final List<String> ADICIONES_VALIDAS = List.of("chispas de chocolate");

    public Galleta() {
        super(); 
    }

    private boolean AdicionGalleta;

    public Galleta(String nombre, double precioVenta, double costoProduccion, int cantidad, boolean AdicionGalleta) {
        super(nombre,precioVenta,costoProduccion,cantidad);
        this.AdicionGalleta= AdicionGalleta;
    }

    @Override
    public void agregarAdicion(Adicion adicion) {
        if (adicion instanceof AdicionGalleta && ADICIONES_VALIDAS.contains(adicion.getNombre().toLowerCase())) {
            super.agregarAdicion(adicion);
        } else {
            System.out.println("Adición inválida para Galleta.");
        }
    }

    public boolean isTieneChispasChocolate() {
        return AdicionGalleta;
    }

    public void setTieneChispasChocolate(boolean AdicionGalleta) {
        this.AdicionGalleta = AdicionGalleta;
    }

    @Override
    public String toString() {
        return "Pan: " + getNombre() + 
            " | Precio: $" + getPrecioVenta() + 
            " | Costo: $" + getCostoProduccion() + 
            " | Cantidad: " + getCantidad() + 
            " | Adición: " + (AdicionGalleta ? "Sí" : "No");
    }
}