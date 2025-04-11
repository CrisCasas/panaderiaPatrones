# 🍞 PanaderíaCSD

Sistema de información para el control de ventas de una panadería, desarrollado en Java con interfaz gráfica usando JavaFX.

---

## 🛠 Tecnologías utilizadas

- **Java 21**
- **Maven**
- **JavaFX 21 (Controls y FXML)**
- **JUnit 3.8.1** para pruebas
- **javafx-maven-plugin** para facilitar la ejecución

---

## 📦 Requisitos previos

### 1. Instalar Java 21

1. Ve a la página oficial de Oracle o adoptium.net para descargar Java 21:
   👉 https://adoptium.net/en-GB/temurin/releases/?version=21
2. Descarga el instalador para **Windows x64 JDK**.
3. Instálalo y **agrega la ruta del JDK al `PATH`** del sistema.
4. Verifica que esté instalado correctamente:
   ```bash
   java -version
5. Debe mostrar algo como:
   ```bash
   java version "21.0.2" 2024-01-16

### 2. Instalar JavaFX SDK

1. Descarga JavaFX SDK 21 desde Gluon: 👉 https://gluonhq.com/products/javafx/

2. Descomprime el archivo ZIP descargado en alguna carpeta, por ejemplo:
   ```bash
   C:\javafx-sdk-21.0.6

3. Anota esta ruta, la vas a necesitar para ejecutar la app.

## 🚀 Cómo ejecutar el programa (Windows)

### 3: Ejecutar el .jar directamente

1. Asegúrate de tener el archivo AppPanaderia.jar.

2. Abre una terminal (CMD o PowerShell) en la carpeta del .jar.

3. Ejecuta el siguiente comando (reemplaza la ruta con la de tu SDK de JavaFX):
   ```bash
   java --module-path "C:\javafx-sdk-21.0.6\lib" --add-modules javafx.controls,javafx.fxml -jar AppPanaderia.jar

