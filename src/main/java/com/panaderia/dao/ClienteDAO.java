package com.panaderia.dao;

import com.panaderia.modelo.personas.Cliente;
import java.util.List;


public class ClienteDAO {
    
    private static final String RUTA_ARCHIVO = "data/clientes.dat";

    public static void guardarClientes(List<Cliente> clientes){
        BinarioUtil.guardarLista(clientes, RUTA_ARCHIVO);
    }

    public static List<Cliente> cargaClientes(){
        return BinarioUtil.cargarLista(RUTA_ARCHIVO);
    }

}
