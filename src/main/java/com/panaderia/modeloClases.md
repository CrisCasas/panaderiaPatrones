classDiagram

%% Jerarquía Persona
class Persona {
  <<abstract>>
  - nombre: String
  - cedula: String
}

class Cliente {
  +comprarProducto()
}

class Administrador {
  - clave: String
  +autenticarse(claveIngresada: String): Boolean
}

Persona <|-- Cliente
Persona <|-- Administrador

%% Jerarquía Producto
class Producto {
  <<abstract>>
  - nombre: String
  - precioVenta: Double
  - costoProduccion: Double
  - cantidad: Int
  - adiciones: List<Adicion>
  +validarCosto(): void
  +validarCantidad(): void
  +agregarAdicion(adicion: Adicion): void
}

class Pan {
  - tieneQueso: Boolean
  +agregarAdicion(adicion: AdicionPan): void
}

class Galleta {
  - tieneChispasChocolate: Boolean
  +agregarAdicion(adicion: AdicionGalleta): void
}

Producto <|-- Pan
Producto <|-- Galleta

%% Conexiones Pan/Galleta con Adiciones
Pan --> "0..*" AdicionPan : contiene >
Galleta --> "0..*" AdicionGalleta : contiene >

%% Jerarquía Adición
class Adicion {
  <<abstract>>
  - nombre: String
  - costo: Double
}

class AdicionPan {
  +detallePan(): void
}

class AdicionGalleta {
  +detalleGalleta(): void
}

Adicion <|-- AdicionPan
Adicion <|-- AdicionGalleta

%% Fábrica de Productos
class FabricaProducto {
  <<interface>>
  +crearProducto(tipo: String, ...): Producto
}

FabricaProducto --> Producto
FabricaProducto --> Pan
FabricaProducto --> Galleta

%% Venta
class Venta {
  - fecha: Date
  - totalVenta: Double
  - productosVendidos: List<Producto>
}

Cliente --> "1..*" Venta : realiza >
Venta --> "1..*" Producto : contiene >

%% Inventario
class Inventario {
  - productos: List<Producto>
  +agregarProducto(producto: Producto): void
  +eliminarProducto(nombre: String): void
}

Administrador --> Inventario : gestiona >
Inventario --> "1..*" Producto : contiene >

%% Sistema de Administración
class SistemaAdministracion {
  - listaProductos: List<Producto>
  - listaVentas: List<Venta>
  +filtrarPorNombre(nombre: String): List<Producto>
  +filtrarPorPrecio(precio: Double): List<Producto>
  +filtrarPorCantidad(cantidad: Int): List<Producto>
  +generarReporteCSV(): void
}

SistemaAdministracion --> Inventario : consulta >
SistemaAdministracion --> Venta : analiza >
Administrador --> SistemaAdministracion : consulta y genera reportes >
