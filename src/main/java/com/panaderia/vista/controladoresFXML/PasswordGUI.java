package com.panaderia.vista.controladoresFXML;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PasswordGUI {

    public static void solicitarContrasena(Runnable onSuccess) {
        Stage dialog = new Stage();
        dialog.setTitle("🔐 Verificación de Administrador");

        Label label = new Label("Ingrese la contraseña:");
        PasswordField passwordField = new PasswordField();
        Button aceptar = new Button("Aceptar");
        Button cancelar = new Button("Cancelar");

        aceptar.setOnAction(e -> {
            if ("admin123".equals(passwordField.getText())) {
                dialog.close();
                onSuccess.run(); // Ejecuta el callback
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Contraseña incorrecta");
                alerta.showAndWait();
            }
        });

        cancelar.setOnAction(e -> dialog.close());

        VBox layout = new VBox(10, label, passwordField, new HBox(10, aceptar, cancelar));
        layout.setStyle("-fx-padding: 20;");
        dialog.setScene(new Scene(layout));
        dialog.show();
    }	
	
}
