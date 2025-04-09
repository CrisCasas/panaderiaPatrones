package com.panaderia.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import com.panaderia.modelo.personas.Administrador;
import com.panaderia.modelo.personas.Cliente;
import com.panaderia.modelo.productos.Producto;
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
    
    public static void generarDesdeArchivoVentas(File archivoDat) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoDat))) {
            List<Venta> ventas = (List<Venta>) ois.readObject();

            String nombreArchivoCSV = "reporte_ventas_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".csv";
            try (PrintWriter writer = new PrintWriter(new FileWriter("data/" + nombreArchivoCSV))) {
                writer.println("Cliente,Fecha,Total,Productos");

                for (Venta venta : ventas) {
                    String nombreCliente = venta.getCliente() != null ? venta.getCliente().getNombre() : "Sin cliente";
                    String productos = venta.getProductosVendidos().stream()
                            .map(Producto::getNombre)
                            .collect(Collectors.joining("|"));

                    writer.printf("%s,%tF,%.2f,%s%n",
                        nombreCliente,
                        venta.getFecha(),
                        venta.getTotalVenta(),
                        productos
                    );
                }

                System.out.println("✅ Reporte de ventas generado con éxito: " + nombreArchivoCSV);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al generar reporte de ventas: " + e.getMessage());
        }
    }

    public static void generarDesdeArchivoProductos(File archivoDat) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoDat))) {
            List<Producto> productos = (List<Producto>) ois.readObject();
    
            String nombreArchivoCSV = "reporte_productos_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".csv";
            try (PrintWriter writer = new PrintWriter(new FileWriter("data/" + nombreArchivoCSV))) {
                writer.println("Nombre,PrecioVenta,CostoProduccion,Cantidad,Adiciones");
    
                for (Producto p : productos) {
                    String adiciones = p.getAdiciones().isEmpty()
                        ? "Ninguna"
                        : p.getAdiciones().stream()
                            .map(a -> a.getNombre() + ":" + a.getCostoProduccion())
                            .collect(Collectors.joining("|"));
    
                    writer.printf("%s,%.2f,%.2f,%d,%s%n",
                        p.getNombre(),
                        p.getPrecioVenta(),
                        p.getCostoProduccion(),
                        p.getCantidad(),
                        adiciones
                    );
                }
    
                System.out.println("✅ Reporte de productos generado con éxito: " + nombreArchivoCSV);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al generar reporte de productos: " + e.getMessage());
        }
    }
    
    public static void generarDesdeArchivoClientes(File archivoDat) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoDat))) {
            List<Cliente> clientes = (List<Cliente>) ois.readObject();

            String nombreArchivoCSV = "reporte_clientes_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".csv";
            try (PrintWriter writer = new PrintWriter(new FileWriter("data/" + nombreArchivoCSV))) {
                writer.println("Nombre,Cédula,VentasRealizadas");

                for (Cliente c : clientes) {
                    int cantidadVentas = c.getVentasRealizadas().size();

                    writer.printf("%s,%s,%d%n",
                        c.getNombre(),
                        c.getCedula(),
                        cantidadVentas
                    );
                }

                System.out.println("✅ Reporte de clientes generado con éxito: " + nombreArchivoCSV);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al generar reporte de clientes: " + e.getMessage());
        }
    }

    public static void generarDesdeArchivoAdministradores(File archivoDat) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoDat))) {
            List<Administrador> administradores = (List<Administrador>) ois.readObject();

            String nombreArchivoCSV = "reporte_administradores_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".csv";
            try (PrintWriter writer = new PrintWriter(new FileWriter("data/" + nombreArchivoCSV))) {
                writer.println("Nombre,Cédula,ClaveEncriptada");

                for (Administrador admin : administradores) {
                    writer.printf("%s,%s,%s%n",
                        admin.getNombre(),
                        admin.getCedula(),
                        admin.getClaveEncriptada()
                    );
                }

                System.out.println("✅ Reporte de administradores generado con éxito: " + nombreArchivoCSV);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al generar reporte de administradores: " + e.getMessage());
        }
    }



}