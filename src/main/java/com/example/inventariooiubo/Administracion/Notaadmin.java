package com.example.inventariooiubo.Administracion;

import com.example.inventariooiubo.MySQLConnection;
import com.example.inventariooiubo.TableRowData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Notaadmin {
        @FXML
        private String casbuscar ="";
        @FXML
        private TextField nombrebuscar;
        @FXML
        private TextField CAS;

        @FXML
        private TextField Nombre;

        @FXML
        public TableView<TableRowData> Tablareactivos;

        @FXML
        private TableColumn<TableRowData, String> TablaCAS;

        @FXML
        private TableColumn<TableRowData, String> TablaNombre;

        @FXML
        private TableColumn<TableRowData, String> TablaVolumen;

        @FXML
        private TableColumn<TableRowData, String> TablaCantidad;

        @FXML
        private TableColumn<TableRowData, String> TablaProveedor;

        @FXML
        private TableColumn<TableRowData, String> TablaGrupo;

        @FXML
        private TableColumn<TableRowData, String> TablaComunes;

        @FXML
        private TableColumn<TableRowData, String> TablaPrivado;

        @FXML
        private TableColumn<TableRowData, String> TablaUbicacion;

        @FXML
        private TableColumn<TableRowData, String> TablaNotas;

        @FXML
        private TextArea comentarionota;

        @FXML
        private TextField id_notas;

        @FXML
        private TextField notasTextArea;

        @FXML
        private TextField nombregrupo_crear;

    public void Todos(ActionEvent actionEvent) {
        try (Connection connection = MySQLConnection.connectDB()) {
            // Consulta SQL para seleccionar todas las notas
            String sql = "SELECT * FROM Reactivos";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Ejecutar la consulta y obtener el resultado
            ResultSet resultSet = statement.executeQuery();

            // Utiliza un StringBuilder para almacenar todas las notas
            StringBuilder notasBuilder = new StringBuilder();
            Pattern pattern = Pattern.compile("(.*?)[:]");
            Pattern pattern1 = Pattern.compile(":(.*?)[.]");

            // Iterar sobre el resultado y agregar todas las notas al StringBuilder
            while (resultSet.next()) {
                String nota = resultSet.getString("notas");
                if (nota != null) { // Verificar si nota no es null
                    Matcher matcherNombre = pattern.matcher(nota); // Patrón para el nombre
                    Matcher matcherComentario = pattern1.matcher(nota); // Patrón para el comentario

                    if (matcherNombre.find() && matcherComentario.find()) {
                        String nombre = matcherNombre.group(1);
                        String comentario = matcherComentario.group(1);

                        notasBuilder.append(nombre+": ").append(comentario).append("\n").append("-----------------------------------------\n");
                    }
                }
            }

            // Mostrar todas las notas en el TextArea
            comentarionota.setText(notasBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void Añadir(ActionEvent actionEvent) {
        try {
            String nombre = nombregrupo_crear.getText(); // Obtener el nombre del grupo desde el campo de texto
            String comentario = notasTextArea.getText(); // Obtener el comentario desde el campo de texto
            String id = id_notas.getText();
            String nota = nombre +": "+ comentario+".";

            // Verificar si el ID está vacío
            if (id.isEmpty()) {
                mostrarMensaje("Error", "Por favor, ingresa un ID de reactivo.|Please enter a reagent ID.");
                return; // Salir del método si no se proporciona un ID
            }

            // Establecer la conexión con la base de datos
            try (Connection connection = MySQLConnection.connectDB()) {
                // Consulta SQL para verificar si el ID del reactivo existe
                String query = "SELECT * FROM reactivos WHERE id = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, id);
                ResultSet resultSet = stmt.executeQuery();

                // Verificar si el ID del reactivo existe
                if (!resultSet.next()) {
                    mostrarMensaje("Error", "El ID del reactivo no existe.|The reagent ID does not exist.");
                    return; // Salir del método si el ID del reactivo no existe
                }

                // Consulta SQL para insertar una nueva nota en la tabla
                String sql = "UPDATE reactivos SET notas = ? WHERE id = ?";

                // Crear una declaración preparada
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(2, id);
                preparedStatement.setString(1, nota); // Establecer el comentario en la consulta

                // Ejecutar la consulta de inserción
                int rowsAffected = preparedStatement.executeUpdate();
                // Confirmar los cambios en la base de datos
                connection.commit();

                // Mostrar mensaje de éxito si la inserción fue exitosa
                if (rowsAffected > 0) {
                    mostrarMensaje("Nota añadida correctamente|Note added successfully", "La nota se ha añadido correctamente.|The note has been added successfully.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Método para mostrar una ventana emergente de alerta con un mensaje
    private void mostrarMensaje(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @FXML
    private void buscarPersona(KeyEvent event) {
        comentarionota.clear();
        String textoBuscar = nombrebuscar.getText();

        try (Connection connection = MySQLConnection.connectDB()) {
            String sql = "SELECT * FROM Reactivos WHERE notas LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + textoBuscar + "%");

            ResultSet resultSet = statement.executeQuery();

            StringBuilder resultado = new StringBuilder();
            Pattern pattern = Pattern.compile("(.*?)[:]");
            Pattern pattern1 = Pattern.compile(":(.*?)[.]");
            // Iterar sobre el resultado y agregar todas las notas al StringBuilder
            while (resultSet.next()) {
                String nota = resultSet.getString("notas");
                if (nota != null) { // Verificar si nota no es null
                    Matcher matcherNombre = pattern.matcher(nota); // Patrón para el nombre
                    Matcher matcherComentario = pattern1.matcher(nota); // Patrón para el comentario

                    if (matcherNombre.find() && matcherComentario.find()) {
                        String nombre = matcherNombre.group(1);
                        String comentario = matcherComentario.group(1);
                        resultado.append(nombre+": ").append(comentario).append("\n").append("-----------------------------------------\n");
                    }
                }
            }

            // Mostrar el resultado en el TextArea
            comentarionota.setText(resultado.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensaje("Error", "Hubo un problema al buscar las notas.");
        }
    }

    public void EntrarListado(ActionEvent event) {
        try {
            // Obtener el MenuItem actual
            MenuItem menuItem = (MenuItem) event.getSource();

            // Obtener la escena actual desde el MenuItem
            Scene scene = menuItem.getParentPopup().getScene();

            // Obtener el escenario actual desde el MenuItem
            Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

            // Cargar el FXML de AdmGrupo.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventariooiubo/Administracion/Listadoadmin.fxml"));
            Parent root = loader.load();

            // Crear la escena
            Scene newScene = new Scene(root);

            // Establecer la nueva escena en el escenario
            stage.setScene(newScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void EntrarAdministracion(ActionEvent event) {
        try {
            // Obtener el MenuItem actual
            MenuItem menuItem = (MenuItem) event.getSource();

            // Obtener la escena actual desde el MenuItem
            Scene scene = menuItem.getParentPopup().getScene();

            // Cargar el FXML de Listado.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventariooiubo/Administracion/administracion.fxml"));
            Parent root = loader.load();

            // Crear la escena
            Scene newScene = new Scene(root);

            // Obtener el escenario actual desde la escena
            Stage stage = (Stage) menuItem.getParentPopup().getOwnerWindow();

            // Establecer la nueva escena en el escenario
            stage.setScene(newScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void CerrarSesion(ActionEvent event) {
        try {
            // Obtener el MenuItem actual
            MenuItem menuItem = (MenuItem) event.getSource();

            // Obtener la escena actual desde el MenuItem
            Scene scene = menuItem.getParentPopup().getScene();

            // Obtener el escenario actual desde el MenuItem
            Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

            // Cargar el FXML de hello-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventariooiubo/hello-view.fxml"));

            Parent root = loader.load();

            // Crear la escena
            Scene newScene = new Scene(root);

            // Establecer la nueva escena en el escenario
            stage.setScene(newScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void EntrarReactivo(ActionEvent event) {
        try {
            // Obtener el MenuItem actual
            MenuItem menuItem = (MenuItem) event.getSource();

            // Obtener la escena actual desde el MenuItem
            Scene scene = menuItem.getParentPopup().getScene();

            // Obtener el escenario actual desde el MenuItem
            Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

            // Cargar el FXML de AdmGrupo.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventariooiubo/Administracion/CrearReactivo.fxml"));
            Parent root = loader.load();

            // Crear la escena
            Scene newScene = new Scene(root);

            // Establecer la nueva escena en el escenario
            stage.setScene(newScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}