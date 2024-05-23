package com.empresa.hitoprogramacion;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Runnable onLoginSuccess;

    public void setOnLoginSuccess(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }

    @FXML
    protected void onLoginButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Verifica si las credenciales son correctas (aquí puedes realizar tu lógica de autenticación)
        if (username.equals("admin") && password.equals("admin")) {
            showAlert("Inicio de sesión exitoso.", Alert.AlertType.INFORMATION);
            if (onLoginSuccess != null) {
                onLoginSuccess.run(); // Llama al oyente de éxito de inicio de sesión
            }
        } else {
            showAlert("Nombre de usuario o contraseña incorrectos.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Mensaje");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
