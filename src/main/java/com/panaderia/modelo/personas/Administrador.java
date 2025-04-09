package com.panaderia.modelo.personas;

import java.io.Serializable;

import com.panaderia.util.Encriptador;

public class Administrador implements Serializable {

    private String nombre;
    private String cedula;
    private String claveEncriptada;

    public Administrador(){
        
    }

    public Administrador(String nombre, String cedula, String clave) {
        this.nombre = nombre;
        this.cedula = cedula;
        setClave(clave); // Encripta automáticamente
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getClaveEncriptada() {
        return claveEncriptada;
    }

    // Al llamar este método, la clave se encripta automáticamente
    public void setClave(String clave) {
        this.claveEncriptada = Encriptador.encriptarSHA256(clave);
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "nombre='" + nombre + '\'' +
                ", cedula='" + cedula + '\'' +
                ", claveEncriptada='" + claveEncriptada + '\'' +
                '}';
    }
}
