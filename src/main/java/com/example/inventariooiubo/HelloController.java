package com.example.inventariooiubo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    public void entrar(ActionEvent event) {
        // Salir de la aplicación
        String uery = "DELETE  FROM Login";

        try (Connection connection = MySQLConnection.connectDB()) {
            // Crear una declaración preparada
            PreparedStatement preparedStatement = connection.prepareStatement(uery);

            // Ejecutar la sentencia SQL para borrar todos los registros
            int rowsAffected = preparedStatement.executeUpdate();
            // Confirmar los cambios en la base de datos
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar cualquier error de SQL que pueda ocurrir
        }
        String username = usernameField.getText();
        String password = passwordField.getText();
        String username1 = username; // Asignar el valor de username a username1
        String password2 = password; // Asignar el valor de password a password2
        String sql = "SELECT * FROM Claves WHERE nombre = ? AND contraseña = ?";
        try (Connection connection = MySQLConnection.connectDB()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username1);
            preparedStatement.setString(2, password2);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (username.equals("admin") && password.equals("nimda")) {
                cargarVistaAdministrador(event);

            }else if (username.equals("user") && password.equals("user")) {
                cargarVistaUsuario(event);

            } else if (resultSet.next()) {
                // Si las credenciales son correctas, cargar la vista correspondiente
                String nombreUsuario = resultSet.getString("nombre");
                String nombrecontraseña = resultSet.getString("contraseña");
                if (username1.equals(nombreUsuario) && password2.equals(nombrecontraseña)) {
                    String insertSql = "INSERT INTO Login (nombre) VALUES (?)";
                    PreparedStatement insertStatement = connection.prepareStatement(insertSql);
                    insertStatement.setString(1, username1);
                    int rowsAffected = insertStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        // Confirmar los cambios en la base de datos
                        connection.commit();
                        cargarVistagrupo(event);
                    }
                }
            }else {
                mostrarMensajeError("Credenciales incorrectas.|Incorrect credentials.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error al acceder a la base de datos");
        }


    }


    public void salir(ActionEvent event) {
        // Salir de la aplicación
        String sql = "DELETE  FROM Login";

        try (Connection connection = MySQLConnection.connectDB()) {
            // Crear una declaración preparada
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Ejecutar la sentencia SQL para borrar todos los registros
            int rowsAffected = preparedStatement.executeUpdate();
            // Confirmar los cambios en la base de datos
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar cualquier error de SQL que pueda ocurrir
        }
        Platform.exit();
    }

    private void cargarVistaAdministrador(ActionEvent event) {
        try {
            // Cargar el FXML de la vista de administrador
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventariooiubo/Administracion/Listadoadmin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void cargarVistagrupo(ActionEvent event) {
        try {
            // Cargar el FXML de la vista de administrador
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventariooiubo/Grupo/Listadogrupo.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void cargarVistaUsuario(ActionEvent event) {
        try {
            // Cargar el FXML de la vista de usuario
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventariooiubo/Usuario/listadouser.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}