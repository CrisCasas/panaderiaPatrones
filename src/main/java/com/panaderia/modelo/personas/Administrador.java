package com.panaderia.modelo.personas;

public class Administrador extends Persona{

    private String clave;
    
    public Administrador(String nombre, String cedula, String clave) {
        super(nombre, cedula);
        this.clave = clave;
    }

    public boolean autenticarse (String claveIngresada){
        return clave != null && clave.equals(claveIngresada);
    }

    public String getClave(){
        return clave;
    }
    
    public void setClave(String clave) {
        this.clave = clave;
    }

}
