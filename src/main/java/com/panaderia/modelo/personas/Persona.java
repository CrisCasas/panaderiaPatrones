package com.panaderia.modelo.personas;

import java.io.Serializable;

public abstract class Persona implements Serializable{
    protected String nombre;
    protected String cedula;

    public Persona(){
        
    }

    public Persona(String nombre, String cedula){
        this.nombre = nombre;
        this.cedula = cedula;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}
