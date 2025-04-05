package com.panaderia.modelo.productos;

import java.util.ArrayList;
import java.util.List;

public abstract class Producto {

    private String nombre;
    private double precioVenta;
    private double costoProduccion;
    private int cantidad;
    private List<Adicion> adiciones = new ArrayList<>();
    
    public Producto(){
        
    }

    public Producto(String nombre, double precioVenta, double costoProduccion, int cantidad){
        this.nombre=nombre;
        this.precioVenta=precioVenta;
        this.costoProduccion=costoProduccion;
        this.cantidad=cantidad;
    }

    public void validarCosto(){
    }
    public void validarCantidad(){
    }
    public void agregarAdicion(Adicion adicion){
        adiciones.add(adicion);
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

    public void reducirStock(){
        if (cantidad>0 && cantidad<=10){
            cantidad--;
        }else{
            System.out.println("No hay stock disponible" + this.nombre);
        }
    }
    
}
