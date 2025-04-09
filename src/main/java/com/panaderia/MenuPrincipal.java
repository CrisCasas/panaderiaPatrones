package com.panaderia;

import java.io.File;
import java.util.*;
import com.panaderia.controlador.*;
import com.panaderia.modelo.personas.Cliente;
import com.panaderia.modelo.personas.Administrador;
import com.panaderia.modelo.productos.*;
import com.panaderia.modelo.sistema.SistemaAdministracion;
import com.panaderia.modelo.ventas.Venta;
import com.panaderia.dao.AdministradorDAO;
import com.panaderia.dao.ProductoDAO;
import com.panaderia.dao.VentaDAO;
import com.panaderia.dao.ClienteDAO;

public class MenuPrincipal {

    private static final String CLAVE_ADMIN = "admin123";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Cargar datos desde archivos binarios
        List<Producto> productos = ProductoDAO.cargarProductos();

        List<Cliente> clientes = ClienteDAO.cargaClientes();
        List<Administrador> admins = AdministradorDAO.cargarAdministradores();
        List<Venta> ventas = VentaDAO.cargarVentas();
        
        // Agregar productos por defecto si no se encontró nada
        boolean productosCargadosPorDefecto = false;

        if (productos.isEmpty()) {
            System.out.println("⚠️ No hay productos disponibles en el inventario.");
            System.out.print("¿Desea ingresar como administrador para agregar productos? (s/n): ");
            String respuesta = sc.nextLine();
        
            if (respuesta.equalsIgnoreCase("s")) {
                System.out.print("Ingrese la clave de administrador: ");
                String clave = sc.nextLine();
        
                if (CLAVE_ADMIN.equals(clave)) {
                    System.out.print("Ingrese su nombre de administrador: ");
                    String nombreAdmin = sc.nextLine();
                    System.out.print("Ingrese su cédula de administrador: ");
                    String cedulaAdmin = sc.nextLine();
                    System.out.println("👤 Bienvenido, " + nombreAdmin + " (C.C. " + cedulaAdmin + ")");
                    
                    // Necesitamos una instancia temporal del sistema y controladores
                    SistemaAdministracion sistemaTemp = new SistemaAdministracion(productos, ventas, clientes, admins);
                    ControladorInventario ctrlInvTemp = new ControladorInventario(sistemaTemp);
                    ControladorAdministrador ctrlAdminTemp = new ControladorAdministrador(sistemaTemp);
        
                    menuAdministrador(sc, ctrlInvTemp, ctrlAdminTemp);
        
                } else {
                    System.out.println("❌ Clave incorrecta. Se agregarán productos de muestra.");
                    productosCargadosPorDefecto = true;
                }
            } else {
                System.out.println("ℹ️ Se agregarán productos de muestra para continuar.");
                productosCargadosPorDefecto = true;
            }
        
            if (productosCargadosPorDefecto) {
                productos.add(new Pan("Pan francés", 1500, 800, 20, false));
                productos.add(new Pan("Pan integral", 1800, 1000, 15, false));
                productos.add(new Galleta("Galleta de avena", 1200, 600, 30, false));
                productos.add(new Galleta("Galleta de chocolate", 1400, 700, 25, false));
            }
        }
        
        SistemaAdministracion sistema = new SistemaAdministracion(productos, ventas, clientes, admins);

        ControladorVentas ctrlVentas = new ControladorVentas(sistema);
        ControladorInventario ctrlInventario = new ControladorInventario(sistema);
        ControladorAdministrador ctrlAdmin = new ControladorAdministrador(sistema);

        boolean salir = false;

        while (!salir) {

            if (productosCargadosPorDefecto) {
                System.out.println("📦 Se cargaron productos de muestra para continuar con la prueba del sistema.");
            }
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
                    // Validar si hay productos disponibles antes de permitir la compra
                    if (ctrlInventario.obtenerInventarioCompleto().isEmpty()) {
                        System.out.println("🚫 No hay productos disponibles para comprar en este momento.");
                        System.out.println("👨‍💼 Por favor, contacte a un administrador para agregar productos.");
                        break; // Volver al menú principal
                    }

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

        // Guardar datos al cerrar el programa
        ProductoDAO.guardarProductos(sistema.getListaProductos());
        ClienteDAO.guardarClientes(sistema.getListaClientes());
        AdministradorDAO.guardarAdministradores(sistema.getListaAdministradores());
        VentaDAO.guardarVentas(sistema.getListaVentas());
        

        System.out.println("¡Gracias por usar el sistema! Datos guardados correctamente.");
        sc.close();
    }

    private static void comprarProductos(Cliente cliente, ControladorVentas ctrlVentas, ControladorInventario ctrlInventario, Scanner sc) {
        List<Producto> inventario = ctrlInventario.obtenerInventarioCompleto();
        
        if (inventario.isEmpty()) {
            System.out.println("⚠️ No hay productos disponibles en el inventario en este momento.");
            System.out.println("📞 Por favor contacte a la administración para que repongan el stock.");
            return; // Salimos del método
        }

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
                    System.out.print("¿Desea añadir adición al producto? (s/n): ");
                    String respuesta = sc.next().toLowerCase();
                    boolean adicion = respuesta.equals("s");


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
                        File carpeta = new File("data");
                            File[] archivosDat = carpeta.listFiles((dir, name) -> name.endsWith(".dat"));

                            if (archivosDat != null && archivosDat.length > 0) {
                                System.out.println("📂 Archivos de ventas disponibles:");
                                for (int i = 0; i < archivosDat.length; i++) {
                                    System.out.println((i + 1) + ". " + archivosDat[i].getName());
                                }

                                System.out.print("Seleccione el número del archivo para generar el reporte: ");
                                int opcionArchivo = sc.nextInt();
                                sc.nextLine(); // limpiar buffer

                                if (opcionArchivo > 0 && opcionArchivo <= archivosDat.length) {
                                    String rutaArchivoSeleccionado = archivosDat[opcionArchivo - 1].getPath();
                                    ctrlAdmin.generarReporteCSVDesdeArchivo(rutaArchivoSeleccionado);
                                } else {
                                    System.out.println("❌ Opción inválida.");
                                }
                            } else {
                                System.out.println("⚠️ No se encontraron archivos .dat en la carpeta.");
                            }
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