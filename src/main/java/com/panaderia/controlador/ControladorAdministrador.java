package com.panaderia.controlador;

import java.io.File;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.panaderia.modelo.sistema.SistemaAdministracion;
import com.panaderia.modelo.ventas.Venta;
import com.panaderia.util.ReporteCSV;

public class ControladorAdministrador {
    
    private SistemaAdministracion sistema;

    public ControladorAdministrador(SistemaAdministracion sistema) {
        this.sistema = sistema;
    }

    // Obtener todas las ventas
    public List<Venta> obtenerTodasLasVentas() {
        return sistema.getListaVentas();
    }

    // Filtrar ventas por fecha exacta
    public List<Venta> obtenerVentasPorFecha(Date fecha) {
        return sistema.getListaVentas().stream()
                .filter(venta -> venta.getFecha().equals(fecha))
                .collect(Collectors.toList());
    }

    // Filtrar ventas por rango de fechas
    public List<Venta> obtenerVentasEntreFechas(Date inicio, Date fin) {
        return sistema.getListaVentas().stream()
                .filter(venta -> !venta.getFecha().before(inicio) && !venta.getFecha().after(fin))
                .collect(Collectors.toList());
    }

    // Calcular ingresos totales en un rango de fechas
    public double calcularTotalVentas(Date inicio, Date fin) {
        return sistema.getListaVentas().stream()
                .filter(venta -> !venta.getFecha().before(inicio) && !venta.getFecha().after(fin))
                .mapToDouble(Venta::getTotalVenta)
                .sum();
    }

    // Generar reporte CSV de ventas a partir de la lista en memoria
    public void generarReporteVentasCSV() {
        List<Venta> ventas = sistema.getListaVentas();
        ReporteCSV.generar(ventas);
    }

    // Generar reporte CSV de ventas entre fechas, usando la lista en memoria
    public void generarReporteVentasCSVEntreFechas(Date inicio, Date fin) {
        List<Venta> ventasFiltradas = obtenerVentasEntreFechas(inicio, fin);
        ReporteCSV.generar(ventasFiltradas);
    }

    // Generar reporte CSV para todos los archivos del tipo ventas en una carpeta específica
    public void generarReporteVentasCSVDesdeRuta(String ruta) {
        ReporteCSV.generarDesdeCarpeta(ruta);
    }

    // Generar reporte CSV a partir de un archivo .dat específico (para ventas)
    public void generarReporteCSVDesdeArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        ReporteCSV.generarDesdeArchivoVentas(archivo);
    }

    // Generar reporte CSV a partir de un archivo .dat específico, según el tipo
    public void generarReporteCSVDesdeArchivo(String rutaArchivo, String tipoArchivo) {
        File archivo = new File(rutaArchivo);
        switch (tipoArchivo) {
            case "ventas":
                ReporteCSV.generarDesdeArchivoVentas(archivo);
                break;
            case "productos":
                ReporteCSV.generarDesdeArchivoProductos(archivo);
                break;
            case "clientes":
                ReporteCSV.generarDesdeArchivoClientes(archivo);
                break;
            case "administradores":
                ReporteCSV.generarDesdeArchivoAdministradores(archivo);
                break;
            default:
                System.out.println("❌ Tipo de archivo no soportado.");
        }
    }
}
