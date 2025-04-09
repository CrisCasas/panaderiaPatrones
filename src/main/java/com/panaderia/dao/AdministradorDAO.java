package com.panaderia.dao;

import com.panaderia.modelo.personas.Administrador;
import java.util.List;

public class AdministradorDAO {
    private static final String RUTA_ARCHIVO = "data/administradores.dat";

    public static void guardarAdministradores(List<Administrador> administradores) {
        BinarioUtil.guardarLista(administradores, RUTA_ARCHIVO);
    }

    public static List<Administrador> cargarAdministradores() {
        return BinarioUtil.cargarLista(RUTA_ARCHIVO);
    }
}
