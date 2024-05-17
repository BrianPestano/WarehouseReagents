package com.example.inventariooiubo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.Connection;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Crear una instancia de MySQLConnection y conectar a la base de datos
        MySQLConnection mysqlConnection = new MySQLConnection();
        Connection connection = mysqlConnection.connectDB();

        // Verificar si la conexión a la base de datos fue exitosa
        if (connection != null) {
            System.out.println("Conexión a la base de datos establecida correctamente.");

            // Crear las tablas en la base de datos
            mysqlConnection.crearTablas(connection);

            // Verificar si las tablas se han creado correctamente
            if (mysqlConnection.tablasExisten(connection)) {
                System.out.println("Las tablas se han creado correctamente.");
            } else {
                System.out.println("Error: No se han podido crear las tablas.");
            }
        } else {
            System.out.println("Error: No se pudo establecer conexión a la base de datos.");
        }

        // Cargar la vista desde el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Establecer la escena en el escenario y mostrarlo
        stage.setScene(scene);
        stage.setResizable(false); // Deshabilitar la capacidad de hacer la ventana grande
        stage.setOnCloseRequest(WindowEvent::consume); // Consumir el evento de cierre para evitar cerrar la aplicación
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}