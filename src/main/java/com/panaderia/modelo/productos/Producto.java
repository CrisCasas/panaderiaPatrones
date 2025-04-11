package com.panaderia.modelo.productos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Producto implements Serializable {

    private String nombre;
    private double precioVenta;
    private double costoProduccion;
    private int cantidad;
    private List<Adicion> adiciones = new ArrayList<>();
    private static final long serialVersionUID = 1L; 
    
    public Producto(){
        
    }

    public Producto(String nombre, double precioVenta, double costoProduccion, int cantidad){
    	if (costoProduccion > precioVenta) {
    	    throw new RuntimeException("❌ Error: el costo de producción no puede ser mayor que el precio de venta.");
    	}
        this.nombre=nombre;
        this.precioVenta=precioVenta;
        this.costoProduccion=costoProduccion;
        this.cantidad=cantidad;
    }

    public void validarCosto(){
    }
    public void validarCantidad(){
    }
    
    public boolean agregarAdicion(Adicion adicion) {
        adiciones.add(adicion);
        return true;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getCostoProduccion() {
        return costoProduccion;
    }

    public void setCostoProduccion(double costoProduccion) {
        this.costoProduccion = costoProduccion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public List<Adicion> getAdiciones(){
        return adiciones;
    }
    
    public void setAdiciones(List<Adicion> adiciones){
        this.adiciones = adiciones;
    }

    public boolean isTieneAdicion() {
        return adiciones != null && !adiciones.isEmpty();
    }
    

    public void reducirStock(){
        if (cantidad>0 && cantidad<=100){
            cantidad--;
        }else{
            System.out.println("No hay stock disponible" + this.nombre);
        }
    }
    
}
