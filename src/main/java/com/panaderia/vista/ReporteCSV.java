package com.panaderia.vista;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.File;
import java.util.ArrayList;

import com.panaderia.modelo.personas.Administrador;
import com.panaderia.modelo.personas.Cliente;
import com.panaderia.modelo.productos.Producto;
import com.panaderia.modelo.ventas.Venta;
import com.panaderia.dao.BinarioUtil;

public class ReporteCSV {

	public static void generar(List<Venta> ventas) {
	    File carpeta = new File("data");
	    if (!carpeta.exists()) {
	        carpeta.mkdir(); // crea la carpeta si no existe
	    }

	    File archivo = new File(carpeta, "reporte_ventas.csv");
	    try (FileWriter writer = new FileWriter(archivo)) {
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

        // Asegurar que la carpeta 'data' exista
        File carpeta = new File("data");
        if (!carpeta.exists()) {
            carpeta.mkdir(); // crear carpeta si no existe
        }

        // Crear nombre completo para guardar el archivo dentro de la carpeta 'data'
        String nombreArchivo = new File(rutaArchivo).getName().replace(".dat", ".csv");
        File archivoCSV = new File(carpeta, nombreArchivo);

        try (FileWriter writer = new FileWriter(archivoCSV)) {
            writer.write("Cliente,Fecha,Total,Productos\n");
            for (Venta venta : ventas) {
                writer.write(venta.toString() + "\n");
            }
            System.out.println("✅ Reporte generado: " + archivoCSV.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("❌ Error al generar el archivo " + archivoCSV.getAbsolutePath());
            e.printStackTrace();
        }
    }

    public static void generarReporteClientes(String ruta) {
        List<Cliente> clientes = BinarioUtil.cargarLista(ruta);
        try (FileWriter writer = new FileWriter("reporte_clientes.csv")) {
            writer.write("Nombre,Cédula\n");
            for (Cliente c : clientes) {
                writer.write(c.getNombre() + "," + c.getCedula() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generarReporteAdministradores(String ruta) {
        List<Administrador> admins = BinarioUtil.cargarLista(ruta);
        try (FileWriter writer = new FileWriter("reporte_administradores.csv")) {
            writer.write("Nombre,Cédula\n");
            for (Administrador a : admins) {
                writer.write(a.getNombre() + "," + a.getCedula() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generarReporteProductos(String ruta) {
        List<Producto> productos = BinarioUtil.cargarLista(ruta);
        try (FileWriter writer = new FileWriter("reporte_productos.csv")) {
            writer.write("Nombre,Precio,Costo,Cantidad,Adición\n");
            for (Producto p : productos) {
                writer.write(p.getNombre() + "," + p.getPrecioVenta() + "," + 
                            p.getCostoProduccion() + "," + p.getCantidad() + "," + 
                            (p.isTieneAdicion() ? "Sí" : "No") + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}