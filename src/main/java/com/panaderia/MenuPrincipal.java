package com.panaderia;

import java.util.*;
import com.panaderia.controlador.*;
import com.panaderia.modelo.personas.Cliente;
import com.panaderia.modelo.productos.*;
import com.panaderia.modelo.sistema.SistemaAdministracion;

public class MenuPrincipal {

    private static final String CLAVE_ADMIN = "admin123";


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SistemaAdministracion sistema = new SistemaAdministracion();
        ControladorVentas ctrlVentas = new ControladorVentas(sistema);
        ControladorInventario ctrlInventario = new ControladorInventario(sistema);
        ControladorAdministrador ctrlAdmin = new ControladorAdministrador(sistema);

        ctrlInventario.agregarProducto(new Pan("panBase", 1000, 600, 10, false));
        ctrlInventario.agregarProducto(new Galleta("galletaBase", 800, 400, 15, false));


        boolean salir = false;

        while (!salir) {
            System.out.println("\n🧁 MENÚ PRINCIPAL 🧁");
            System.out.println("1. Comprar productos");
            System.out.println("2. Buscar producto por nombre");
            System.out.println("3. Buscar productos por rango de precio");
            System.out.println("4. Ver productos con bajo stock");
            System.out.println("5. Ingresar como administrador");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese su nombre: ");
                    String nombreCliente = sc.nextLine();
                    System.out.print("Ingrese su número de cédula: ");
                    String cedulaCliente = sc.nextLine();
                    Cliente cliente = new Cliente(nombreCliente, cedulaCliente);
                    comprarProductos(cliente, ctrlVentas, ctrlInventario, sc);
                    break;
                case 2:
                    System.out.print("Ingrese nombre o parte del nombre del producto: ");
                    String nombre = sc.nextLine();
                    List<Producto> encontrados = ctrlInventario.buscarProductoPorNombre(nombre);
                    encontrados.forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("Precio mínimo: ");
                    double min = sc.nextDouble();
                    System.out.print("Precio máximo: ");
                    double max = sc.nextDouble();
                    List<Producto> porPrecio = ctrlInventario.buscarProductosPorRangoPrecio(min, max);
                    porPrecio.forEach(System.out::println);
                    break;
                case 4:
                    System.out.print("Ingrese el límite de stock: ");
                    int limite = sc.nextInt();
                    List<Producto> bajoStock = ctrlInventario.obtenerProductosConStockBajo(limite);
                    bajoStock.forEach(System.out::println);
                    break;
                case 5:
                    System.out.print("Ingrese la clave de administrador: ");
                    String clave = sc.nextLine();
                    if (CLAVE_ADMIN.equals(clave)) {
                        System.out.print("Ingrese su nombre de administrador: ");
                        String nombreAdmin = sc.nextLine();
                        System.out.print("Ingrese su cédula de administrador: ");
                        String cedulaAdmin = sc.nextLine();
                        System.out.println("👤 Bienvenido, " + nombreAdmin + " (C.C. " + cedulaAdmin + ")");
                        menuAdministrador(sc, ctrlInventario, ctrlAdmin);
                    } else {
                        System.out.println("❌ Clave incorrecta.");
                    }
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("⚠️ Opción no válida.");
            }
        }

        System.out.println("¡Gracias por usar el sistema!");
        sc.close();
    }

    private static void comprarProductos(Cliente cliente, ControladorVentas ctrlVentas, ControladorInventario ctrlInventario, Scanner sc) {
        List<Producto> inventario = ctrlInventario.obtenerInventarioCompleto();
        System.out.println("Productos disponibles:");
        for (int i = 0; i < inventario.size(); i++) {
            System.out.println((i + 1) + ". " + inventario.get(i));
        }
    
        List<Producto> carrito = new ArrayList<>();
        String continuar;
    
        do {
            System.out.print("Seleccione el número del producto: ");
            int indice = sc.nextInt() - 1;
            Producto base = inventario.get(indice);
            sc.nextLine(); // limpiar buffer
    
            if (base instanceof Pan) {
                Pan pan = (Pan) base;
                System.out.print("¿Desea añadir queso al pan? (s/n): ");
                if (sc.nextLine().equalsIgnoreCase("s")) {
                    Adicion queso = new AdicionPan("queso",100);
                    pan.agregarAdicion(queso);
                }
                carrito.add(pan);
            } else if (base instanceof Galleta) {
                Galleta galleta = (Galleta) base;
                System.out.print("¿Desea añadir chispas de chocolate a la galleta? (s/n): ");
                if (sc.nextLine().equalsIgnoreCase("s")) {
                    Adicion chispas = new AdicionGalleta("chispas de chocolate",500);
                    galleta.agregarAdicion(chispas);
                }
                carrito.add(galleta);
            } else {
                carrito.add(base);
            }
    
            System.out.print("¿Desea agregar otro producto? (s/n): ");
            continuar = sc.nextLine();
        } while (continuar.equalsIgnoreCase("s"));
    
        boolean exito = ctrlVentas.registrarVenta(cliente, carrito);
        if (exito) {
            System.out.println("✅ Compra realizada con éxito.");
        } else {
            System.out.println("❌ No se pudo realizar la compra. Verifique el stock.");
        }
    }
    

    private static void menuAdministrador(Scanner sc, ControladorInventario ctrlInventario, ControladorAdministrador ctrlAdmin) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n🔐 MENÚ ADMINISTRADOR 🔐");
            System.out.println("1. Agregar producto");
            System.out.println("2. Eliminar producto");
            System.out.println("3. Actualizar cantidad");
            System.out.println("4. Generar reporte CSV de ventas");
            System.out.println("5. Ver total de ingresos en un rango de fechas");
            System.out.println("0. Salir del modo administrador");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("¿Qué tipo de producto desea agregar? (pan/galleta): ");
                    String tipo = sc.nextLine().toLowerCase();
                    System.out.print("Nombre del producto: ");
                    String nombre = sc.nextLine();
                    System.out.print("Precio de venta: ");
                    double precio = sc.nextDouble();
                    System.out.println("Costo de Producción: ");
                    double costoProduccion= sc.nextDouble();
                    System.out.print("Cantidad: ");
                    int cantidad = sc.nextInt();
                    System.out.println("Adicion: ");
                    boolean adicion = sc.nextBoolean();

                    Producto nuevoProducto;
                    if (tipo.equals("pan")) {
                        nuevoProducto = new Pan(nombre, precio, costoProduccion, cantidad,adicion);
                    } else if (tipo.equals("galleta")) {
                        nuevoProducto = new Galleta(nombre, precio, costoProduccion, cantidad,adicion );
                    } else {
                        System.out.println("❌ Tipo de producto no válido.");
                        break;
                    }

                    ctrlInventario.agregarProducto(nuevoProducto);
                    System.out.println("✅ Producto agregado.");
                    break;
                case 2:
                    System.out.print("Nombre del producto a eliminar: ");
                    String eliminar = sc.nextLine();
                    boolean eliminado = ctrlInventario.eliminarProducto(eliminar);
                    System.out.println(eliminado ? "✅ Producto eliminado." : "❌ Producto no encontrado.");
                    break;
                case 3:
                    System.out.print("Nombre del producto: ");
                    String nombreActualizar = sc.nextLine();
                    System.out.print("Nueva cantidad: ");
                    int nuevaCantidad = sc.nextInt();
                    boolean actualizado = ctrlInventario.actualizarCantidadProducto(nombreActualizar, nuevaCantidad);
                    System.out.println(actualizado ? "✅ Cantidad actualizada." : "❌ Producto no encontrado.");
                    break;
                case 4:
                    ctrlAdmin.generarReporteVentasCSV();
                    System.out.println("📄 Reporte generado como 'reporte_ventas.csv'.");
                    break;
                case 5:
                    System.out.print("Ingrese fecha inicio (yyyy-MM-dd): ");
                    Date inicio = leerFecha(sc);
                    System.out.print("Ingrese fecha fin (yyyy-MM-dd): ");
                    Date fin = leerFecha(sc);
                    double total = ctrlAdmin.calcularTotalVentas(new java.sql.Date(inicio.getTime()), new java.sql.Date(fin.getTime()));
                    System.out.println("💰 Total ventas: $" + total);
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("⚠️ Opción no válida.");
            }
        }
    }

    private static Date leerFecha(Scanner sc) {
        try {
            String input = sc.nextLine();
            return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(input);
        } catch (Exception e) {
            System.out.println("❌ Formato incorrecto. Se usará la fecha actual.");
            return new Date();
        }
    }
}
