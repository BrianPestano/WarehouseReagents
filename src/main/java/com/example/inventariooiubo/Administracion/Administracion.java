package com.example.inventariooiubo.Administracion;

import com.example.inventariooiubo.MySQLConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Administracion {
        @FXML
        private TextField nombregrupo_crear;

        @FXML
        private TextField contraseña;

        @FXML
        private TextArea vergrupos_bucar;

        @FXML
        private TextField nombre_borar;

    public void creargrupo(ActionEvent event) {
        // Obtener los datos del formulario
        String nombreGrupo = nombregrupo_crear.getText();
        String contraseñaClave = contraseña.getText();

        // Verificar que los campos no estén vacíos y cumplan con ciertos criterios
        if (campoVacio(nombreGrupo, contraseñaClave)) {
            // Al menos uno de los campos está vacío, mostrar una alerta
            mostrarAlerta("Error", "No se pueden dejar campos vacíos.|Fields cannot be left empty.");
            return;
        }
        if (!validarCampos(nombreGrupo, contraseñaClave)) {
            mostrarAlerta("Error", "Los campos deben contener solo letras.|Fields must contain only letters.");
            return;
        }

        // Insertar los datos en la tabla "clave"
        try (Connection connection = MySQLConnection.connectDB()) {
            String sql = "INSERT INTO Claves (nombre, contraseña) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nombreGrupo);
            statement.setString(2, contraseñaClave);

            // Ejecutar la sentencia SQL
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                connection.commit();
                // Se insertaron los datos correctamente, mostrar un mensaje de éxito
                mostrarAlerta("Éxito|Success", "Los datos se han insertado correctamente en la tabla clave.|The data has been successfully inserted into the key table.");
                nombregrupo_crear.clear();
                contraseña.clear();
            } else {
                // Hubo un problema al insertar los datos, mostrar un mensaje de error
                mostrarAlerta("Error", "Hubo un problema al insertar los datos en la tabla clave.|\n" +
                        "There was a problem inserting the data into the key table.");
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción SQL que pueda ocurrir
            e.printStackTrace();
            mostrarAlerta("Error", "Hubo un problema por que ese nombre de grupo ya existe.|\n" +
                    "There was a problem because that group name already exists.");
        }
    }
        private boolean campoVacio(String... campos) {
            for (String campo : campos) {
                if (campo == null || campo.trim().isEmpty()) {
                    return true; // Si encuentra un campo vacío, devuelve true
                }
            }
            return false; // Si ninguno de los campos está vacío, devuelve false
        }
        private boolean validarCampos(String... campos) {
            String regex = "^[a-zA-ZáéíóúüÁÉÍÓÚÜ\\s]+$";
            for (String campo : campos) {
                if (!campo.matches(regex)) {
                    return false;
                }
            }
            return true;
        }
        // Método para mostrar una alerta
        private void mostrarAlerta(String titulo, String mensaje) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(titulo);
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();
        }


        public void vergrupos(ActionEvent event) {
            // Limpiar el contenido actual del TextArea
            vergrupos_bucar.clear();

            try (Connection connection = MySQLConnection.connectDB()) {
                // Preparar la sentencia SQL para seleccionar todos los nombres de los grupos
                String sql = "SELECT * FROM Claves";
                PreparedStatement statement = connection.prepareStatement(sql);

                // Ejecutar la consulta SQL
                ResultSet resultSet = statement.executeQuery();

                // Recorrer los resultados y agregar los nombres de los grupos al TextArea
                while (resultSet.next()) {
                    String nombreGrupo = resultSet.getString("nombre");
                    String contraseña = resultSet.getString("contraseña");
                    vergrupos_bucar.appendText("Denominacion: "+nombreGrupo + "\n"+"Contraseña: "+contraseña+ "\n"+"--------------\n");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Manejar cualquier error de base de datos aquí
                mostrarAlerta("Error", "Hubo un problema al obtener los nombres de los grupos.|There was a problem getting the group names.");
            }
        }
        public void borargrupo(ActionEvent event) {
            // Implementar lógica para borrar un grupo
            String nombreGrupo = nombre_borar.getText();
            if (campoVacio(nombreGrupo)) {
                // Al menos uno de los campos está vacío, mostrar una alerta
                mostrarAlerta("Error", "No se pueden dejar campos vacíos.|Fields cannot be left empty.");
                return;
            }
            if (!validarCampos(nombreGrupo)) {
                mostrarAlerta("Error", "Los campos deben contener solo letras.|Fields must contain only letters.");
                return;
            }
            // Por ahora, simplemente eliminaremos el grupo con el nombre proporcionado
            boolean eliminacionExitosa = eliminarGrupoPorNombre(nombreGrupo);

            // Verificar si la eliminación fue exitosa
            if (!eliminacionExitosa) {
                mostrarAlerta("Éxito|Succes", "Grupo|Group '" + nombreGrupo + "' eliminado exitosamente.|successfully removed.");
                nombre_borar.clear();
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el grupo|Could not delete group '" + nombreGrupo + "'.");
            }
        }
        // Método para eliminar un grupo por su nombre
        private boolean eliminarGrupoPorNombre(String nombreGrupo) {
            try (Connection connection = MySQLConnection.connectDB()) {
                // Preparar la sentencia SQL para eliminar el grupo por su nombre
                String deleteSql = "DELETE FROM Claves WHERE LOWER(nombre) = LOWER(?)"; // Ignorar diferencias de mayúsculas y minúsculas
                PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                deleteStatement.setString(1, nombreGrupo);

                // Ejecutar la sentencia SQL para eliminar el grupo
                int filasAfectadasDelete = deleteStatement.executeUpdate();
                connection.commit();

                // Preparar la sentencia SQL para actualizar los registros en la tabla reactivos
                String updateSql = "UPDATE reactivos SET grupo = '', comunes = true, privado = false WHERE LOWER(grupo) = LOWER(?)"; // Ignorar diferencias de mayúsculas y minúsculas
                PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, nombreGrupo);

                // Ejecutar la sentencia SQL para actualizar los registros en la tabla reactivos
                int filasAfectadasUpdate = updateStatement.executeUpdate();

                // Confirmar la transacción
                connection.commit();

                // Devolver true si se eliminó correctamente al menos un registro en Claves
                // y si se actualizaron correctamente al menos un registro en reactivos
                return filasAfectadasDelete > 0 && filasAfectadasUpdate > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        public void EntrarNotas(ActionEvent event) {
            try {
                // Obtener el MenuItem actual
                MenuItem menuItem = (MenuItem) event.getSource();

                // Obtener la escena actual desde el MenuItem
                Scene scene = menuItem.getParentPopup().getScene();

                // Obtener el escenario actual desde el MenuItem
                Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

                // Cargar el FXML de AdmGrupo.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventariooiubo/Administracion/Notaadmin.fxml"));
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
        public void EntrarListado(ActionEvent event) {
            try {
                // Obtener el MenuItem actual
                MenuItem menuItem = (MenuItem) event.getSource();

                // Obtener la escena actual desde el MenuItem
                Scene scene = menuItem.getParentPopup().getScene();

                // Cargar el FXML de Listado.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventariooiubo/Administracion/listadoadmin.fxml"));
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
        salir();

        try {
            // Obtener el MenuItem actual
            MenuItem menuItem = (MenuItem) event.getSource();

            // Obtener el escenario actual desde el MenuItem
            Stage stage = (Stage) menuItem.getParentPopup().getOwnerWindow();

            // Cargar el FXML de hello-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventariooiubo/hello-view.fxml"));
            Parent root = loader.load();

            // Crear la escena
            Scene newScene = new Scene(root);

            // Establecer la nueva escena en el escenario
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void salir() {
        // Salir de la aplicación
        String sql = "TRUNCATE TABLE Login";

        try (Connection connection = MySQLConnection.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Ejecutar la sentencia SQL para truncar la tabla
            preparedStatement.executeUpdate();
            // Confirmar los cambios en la base de datos
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar cualquier error de SQL que pueda ocurrir
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