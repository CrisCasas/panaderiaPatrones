package com.panaderia.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class BinarioUtil {

    public static <T> void guardarLista(List<T> lista, String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            File carpetaPadre = archivo.getParentFile();
            if (carpetaPadre != null && !carpetaPadre.exists()) {
                carpetaPadre.mkdirs();
            }

            // Si el archivo es ventas.dat, agregar fecha
            if (archivo.getName().equals("ventas.dat")) {
                String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
                String nuevoNombre = "ventas_" + timestamp + ".dat";
                archivo = new File(archivo.getParent(), nuevoNombre);
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
                oos.writeObject(lista);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> cargarLista(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
