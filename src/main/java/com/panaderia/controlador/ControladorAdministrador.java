package com.panaderia.controlador;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.panaderia.modelo.sistema.SistemaAdministracion;
import com.panaderia.modelo.ventas.Venta;
import com.panaderia.util.ReporteCSV;

public class ControladorAdministrador {
    
    private SistemaAdministracion sistema;

    public ControladorAdministrador(SistemaAdministracion sistema){
        this.sistema = sistema;
    }

    // Obtener todas las ventas
    public List<Venta> obtenerTodasLasVentas(){
        return sistema.getListaVentas();
    }

    // Filtrar por fecha exacta
    public List<Venta> obtenerVentasPorFecha(Date fecha){
        return sistema.getListaVentas().stream()
        .filter(venta-> venta.getFecha().equals(fecha))
        .collect(Collectors.toList());
    }

    // Filtrar ventas por rango de fechas
    public List<Venta> obtenerVentasEntreFechas(Date inicio, Date fin) {
        return sistema.getListaVentas().stream()
            .filter(venta -> !venta.getFecha().before(inicio) && !venta.getFecha().after(fin))
            .collect(Collectors.toList());
    }

    // Calcular ingresos totales en un rango de fechas

    public double calcularTotalVentas(Date inicio, Date fin){

        return sistema.getListaVentas().stream()
            .filter(venta -> !venta.getFecha().before(inicio) && !venta.getFecha().after(fin))
            .mapToDouble(Venta::getTotalVenta)
            .sum();

    }

    // Genrerar archivo CSV con todas las ventas

    public void generarReporteVentasCSV(){
        
        List<Venta> ventas = sistema.getListaVentas();
        ReporteCSV.generar(ventas); 

    }

    //Gnerar Reporte CSV con ventas en un rango especifico
    public void generarReporteVentasCSVEntreFechas(Date inicio,Date fin){

        List<Venta> ventasFiltradas = obtenerVentasEntreFechas(inicio, fin);
        ReporteCSV.generar(ventasFiltradas);

    }

}
