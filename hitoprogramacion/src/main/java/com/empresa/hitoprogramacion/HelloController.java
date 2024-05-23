package com.empresa.hitoprogramacion;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class HelloController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField ageField;

    @FXML
    private ComboBox<String> modelComboBox;

    @FXML
    private TextField priceField;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, Integer> ageColumn;

    @FXML
    private TableColumn<User, String> modelColumn;

    @FXML
    private TableColumn<User, Double> priceColumn;

    @FXML
    private TextField filterField;

    private ObservableList<User> originalData; // Mantén una copia de los datos originales para restaurar después de borrar el filtro

    private Map<String, Double> modelPriceMap;

    private MongoClient mongoClient;
    private MongoDatabase database;

    public void initialize() {
        try {
            // Conectar a la base de datos MongoDB
            mongoClient = MongoClients.create("mongodb+srv://alexmenedez:123@cluster0.yx7rzjk.mongodb.net/");
            database = mongoClient.getDatabase("hitoprogramacion");

            // Inicializar modelos y precios
            modelPriceMap = new HashMap<>();
            modelPriceMap.put("Honda Civic", 22000.0);
            modelPriceMap.put("Honda Accord", 25000.0);
            modelPriceMap.put("Honda CR-V", 30000.0);

            modelComboBox.setItems(FXCollections.observableArrayList(modelPriceMap.keySet()));
            modelComboBox.setOnAction(event -> {
                String selectedModel = modelComboBox.getSelectionModel().getSelectedItem();
                priceField.setText(modelPriceMap.get(selectedModel).toString());
            });

            // Inicializar la tabla
            nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
            modelColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
            priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

            // Guarda una copia de los datos originales de la tabla
            originalData = FXCollections.observableArrayList(userTable.getItems());

            // Agrega un listener al campo de filtro para realizar el filtrado
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    // Usa el filtro para buscar coincidencias en los datos originales
                    userTable.getItems().clear();
                    originalData.stream()
                            .filter(filterByName(newValue))
                            .forEach(userTable.getItems()::add);
                } catch (Exception e) {
                    showAlert("Error al aplicar el filtro: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            showAlert("Error en la inicialización: " + e.getMessage());
        }
    }

    // Método para filtrar por nombre
    private Predicate<User> filterByName(String name) {
        return user -> {
            try {
                // Si el campo de filtro está vacío, muestra todos los usuarios
                if (name == null || name.isEmpty()) {
                    return true;
                }
                // Filtra por nombre
                return user.getName().toLowerCase().contains(name.toLowerCase());
            } catch (Exception e) {
                showAlert("Error al aplicar el filtro: " + e.getMessage());
                return false;
            }
        };
    }

    @FXML
    protected void onAddButtonClick() {
        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String model = modelComboBox.getSelectionModel().getSelectedItem();
            double price = modelPriceMap.get(model);

            User newUser = new User(name, age, model, price);
            userTable.getItems().add(newUser);

            clearFields(); // Limpia los campos después de agregar el usuario
        } catch (NumberFormatException e) {
            showAlert("Por favor, ingrese una edad válida.");
        } catch (Exception e) {
            showAlert("Error al agregar usuario: " + e.getMessage());
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        try {
            User selectedUser = userTable.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                userTable.getItems().remove(selectedUser);
            } else {
                showAlert("Seleccione un usuario para eliminar.");
            }
        } catch (Exception e) {
            showAlert("Error al eliminar usuario: " + e.getMessage());
        }
    }

    @FXML
    protected void onUpdateButtonClick() {
        try {
            User selectedUser = userTable.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String model = modelComboBox.getSelectionModel().getSelectedItem();
                double price = modelPriceMap.get(model);

                selectedUser.setName(name);
                selectedUser.setAge(age);
                selectedUser.setModel(model);
                selectedUser.setPrice(price);

                userTable.refresh(); // Actualiza la tabla después de la modificación
                clearFields(); // Limpia los campos después de la modificación
            } else {
                showAlert("Seleccione un usuario para actualizar.");
            }
        } catch (NumberFormatException e) {
            showAlert("Por favor, ingrese una edad válida.");
        } catch (Exception e) {
            showAlert("Error al actualizar usuario: " + e.getMessage());
        }
    }

    private void clearFields() {
        nameField.clear();
        ageField.clear();
        modelComboBox.getSelectionModel().clearSelection();
        priceField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

