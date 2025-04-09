package com.panaderia.modelo.productos;

import java.io.Serializable;

public abstract class Adicion implements Serializable {
    private String nombre;
    private double precioVenta;
    private double costoProduccion;

    public Adicion(String nombre, double precioVenta, double costoProduccion) {
        validarCostos(precioVenta, costoProduccion);
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.costoProduccion = costoProduccion;
    }

    private void validarCostos(double precioVenta, double costoProduccion) {
        if (costoProduccion > precioVenta) {
            throw new IllegalArgumentException("❌ El costo de producción no puede ser mayor al precio de venta.");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        validarCostos(precioVenta, this.costoProduccion);
        this.precioVenta = precioVenta;
    }

    public double getCostoProduccion() {
        return costoProduccion;
    }

    public void setCostoProduccion(double costoProduccion) {
        validarCostos(this.precioVenta, costoProduccion);
        this.costoProduccion = costoProduccion;
    }

    public abstract String tipo(); // "Pan" o "Galleta"
}

