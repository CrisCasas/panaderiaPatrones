package com.panaderia.vista.controladoresFXML;


import java.util.List;

import com.panaderia.modelo.productos.Producto;
import com.panaderia.vista.BuscarPorNombreGUI;
import com.panaderia.vista.BuscarPorPrecioGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    public void comprarProductos(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/panaderia/vista/CompraProductos.fxml"));
            Stage stage = new Stage();
            stage.setTitle("🛒 Compra de Productos");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void buscarPorNombre(ActionEvent event) {
        System.out.println("🔍 Buscar producto por nombre");
        try {
           new BuscarPorNombreGUI();  // <-- abre la ventana
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void buscarPorPrecio(ActionEvent event) {
        System.out.println("💲 Buscar producto por precio");
        try {
          new BuscarPorPrecioGUI();  // <-- abre la ventana
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ingresarAdministrador(ActionEvent event) {
        System.out.println("🔐 Modo administrador");
        PasswordGUI.solicitarContrasena(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/com/panaderia/vista/AdminGUI.fxml"));
                Stage stage = new Stage();
                stage.setTitle("🔐 Panel Administrador");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });       
    }

    @FXML
    public void salir(ActionEvent event) {
        System.out.println("👋 Saliendo...");
        
        // Guardar el inventario antes de cerrar
        List<Producto> productos = com.panaderia.SistemaCache.getInstance()
                        .getCtrlInventario().obtenerInventarioCompleto();
        com.panaderia.dao.ProductoDAO.guardarProductos(productos);

        System.exit(0);
    }

}
