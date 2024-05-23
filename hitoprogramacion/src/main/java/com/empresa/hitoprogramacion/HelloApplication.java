package com.empresa.hitoprogramacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(HelloApplication.class.getResource("/com/empresa/hitoprogramacion/login-view.fxml"));
        Parent loginRoot = loginLoader.load();
        Scene loginScene = new Scene(loginRoot, 600, 400);
        stage.setTitle("Concesionario de Coches - Iniciar Sesión / Registrarse");
        stage.setScene(loginScene);
        stage.show();

        // Obtener el controlador del login
        LoginController loginController = loginLoader.getController();

        // Configurar un listener para el evento de inicio de sesión
        loginController.setOnLoginSuccess(() -> {
            try {
                FXMLLoader helloLoader = new FXMLLoader(HelloApplication.class.getResource("/com/empresa/hitoprogramacion/hello-view.fxml"));
                Parent helloRoot = helloLoader.load();
                Scene helloScene = new Scene(helloRoot, 800, 600);
                stage.setScene(helloScene);
                stage.setTitle("Concesionario de Coches - Bienvenido");
            } catch (IOException e) {
                e.printStackTrace(); // Maneja el error adecuadamente en tu aplicación
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
