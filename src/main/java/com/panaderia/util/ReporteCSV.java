package com.panaderia.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.panaderia.modelo.ventas.Venta;

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
    
    
}