package com.panaderia.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.File;
import java.util.ArrayList;

import com.panaderia.modelo.ventas.Venta;
import com.panaderia.dao.BinarioUtil;

public class ReporteCSV {

    public static void generar(List<Venta> ventas) {
        try (FileWriter writer = new FileWriter("reporte_ventas.csv")) {
            writer.write("Cliente,Fecha,Total,Productos\n");
            for (Venta venta : ventas) {
                writer.write(venta.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public static void generarDesdeCarpeta(String carpeta) {
        List<Venta> todasLasVentas = new ArrayList<>();

        File dir = new File(carpeta);
        if (dir.exists() && dir.isDirectory()) {
            File[] archivos = dir.listFiles((d, name) -> name.startsWith("ventas") && name.endsWith(".dat"));
            if (archivos != null) {
                for (File archivo : archivos) {
                    List<Venta> ventasArchivo = BinarioUtil.cargarLista(archivo.getPath());
                    todasLasVentas.addAll(ventasArchivo);
                }
            }
        }

        try (FileWriter writer = new FileWriter("reporte_ventas.csv")) {
            writer.write("Cliente,Fecha,Total,Productos\n");
            for (Venta venta : todasLasVentas) {
                writer.write(venta.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generarDesdeArchivoEspecifico(String rutaArchivo) {
        List<Venta> ventas = BinarioUtil.cargarLista(rutaArchivo);
    
        String nombreCSV = new File(rutaArchivo).getName().replace(".dat", ".csv");
    
        try (FileWriter writer = new FileWriter(nombreCSV)) {
            writer.write("Cliente,Fecha,Total,Productos\n");
            for (Venta venta : ventas) {
                writer.write(venta.toString() + "\n");
            }
            System.out.println("✅ Reporte generado: " + nombreCSV);
        } catch (IOException e) {
            System.out.println("❌ Error al generar el archivo " + nombreCSV);
            e.printStackTrace();
        }
    }
    
}