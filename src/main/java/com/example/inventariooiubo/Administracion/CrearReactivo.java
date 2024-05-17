package com.example.inventariooiubo.Administracion;

import com.example.inventariooiubo.MySQLConnection;
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

public class CrearReactivo {
    @FXML
    private TextField cas;
    @FXML
    private TextField volumen;
    @FXML
    private TextField IDCREAR;
    @FXML
    private TextField nombre;
    @FXML
    private TextField Cantidad;
    @FXML
    private TextField localizacion;
    @FXML
    private TextField grupo;
    @FXML
    private TextField proveedor ;
    @FXML
    private CheckBox privado ;
    @FXML
    private CheckBox comunes;
    @FXML
    private TextArea vercomprovacion;
    @FXML
    private TextField idborarr;

    @FXML
    private void initialize() {

        // Agregar un controlador de eventos a privado para desactivar comunes
        privado.setOnAction(event -> {
            if (privado.isSelected()) {
                comunes.setSelected(false);
            }
        });

        // Agregar un controlador de eventos a comunes para desactivar privado
        comunes.setOnAction(event -> {
            if (comunes.isSelected()) {
                privado.setSelected(false);
            }
        });
    }

    @FXML
    private void crearReactivo(ActionEvent event) {
        // Obtener los valores ingresados por el usuario
        String casValue = cas.getText();
        if (casValue.matches("[0-9\\-]+")) {
            // La entrada contiene solo números y guiones
            // Puedes continuar con el procesamiento
        } else {
            // La entrada contiene caracteres no válidos
            // Mostrar un mensaje de alerta al usuario
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Entrada no válida");
            alert.setContentText("La entrada debe contener solo números y guiones.");
            alert.showAndWait();
            return;
        }
        String nombreValue = nombre.getText();
        String volumenValue = volumen.getText();
        String cantidadValue = Cantidad.getText();


        // Verificar si la cantidad ingresada es un número
        try {
            double cantidadNumerica = Double.parseDouble(cantidadValue);
            // Si la cantidad no es un número válido, lanzará NumberFormatException
            // En ese caso, se manejará en el bloque catch a continuación
        } catch (NumberFormatException e) {
            // La cantidad ingresada no es un número válido
            // Mostrar un mensaje de error al usuario
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("La cantidad ingresada no es un número válido.|\n" +
                    "The amount entered is not a valid number.");
            alert.showAndWait();
            return; // Salir del método ya que la cantidad no es válida
        }
        String proveedorValue = proveedor.getText();
        String grupoValue = grupo.getText();

        String localizacionValue = localizacion.getText();
        // Verificar si alguno de los campos obligatorios está vacío
        if (casValue.isEmpty() || nombreValue.isEmpty() || volumenValue.isEmpty() || cantidadValue.isEmpty() || proveedorValue.isEmpty() || localizacionValue.isEmpty()) {
            // Mostrar un mensaje de error al usuario
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete todos los campos obligatorios.|Please complete all required fields.");
            alert.showAndWait();
            return; // Salir del método ya que algún campo está vacío
        }
        //Verificar si es comun o privado
        boolean comunesValue = comunes.isSelected();
        boolean privadoValue = privado.isSelected();
        if(comunesValue == true){
            privadoValue = false;
        }
        if(privadoValue == true){
            comunesValue=false;
        }
        if (grupoValue.isEmpty()){
            comunesValue=true;
            privadoValue = false;
        }else{
            comunesValue=false;
        }


        try (Connection connection = MySQLConnection.connectDB()) {
            // Establecer el autocommit en false para desactivarlo y agrupar las operaciones en una transacción
            connection.setAutoCommit(false);
            try {
                // Consulta para verificar si el nombre del grupo existe en la tabla Claves
                String checkGroupNameQuery = "SELECT * FROM Claves WHERE nombre = ?";
                PreparedStatement checkStatement = connection.prepareStatement(checkGroupNameQuery);
                checkStatement.setString(1, grupoValue);
                ResultSet resultSet = checkStatement.executeQuery();

                if (grupoValue.isEmpty() || resultSet.next()) {
                    // Si el campo del grupo está vacío o el nombre del grupo existe en la tabla Claves
                    // Procede con la inserción del reactivo

                    // Consulta para insertar el reactivo en la tabla Reactivos
                    String insertQuery = "INSERT INTO Reactivos (cas, nombre, volumen, cantidad, Proveedor, grupo, localizacion, comunes, privado) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(insertQuery);
                    statement.setString(1, casValue);
                    statement.setString(2, nombreValue);
                    statement.setString(3, volumenValue);
                    statement.setString(4, cantidadValue);
                    statement.setString(5, proveedorValue);
                    statement.setString(6, grupoValue);
                    statement.setString(7, localizacionValue);
                    statement.setBoolean(8, comunesValue);
                    statement.setBoolean(9, privadoValue);

                    // Ejecutar la inserción del reactivo
                    int rowsInserted = statement.executeUpdate();

                    if (rowsInserted > 0) {
                        // Se insertó el reactivo correctamente
                        // Mostrar un mensaje de éxito al usuario
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Éxito|Succes");
                        alert.setHeaderText(null);
                        alert.setContentText("El reactivo se ha creado correctamente.|The reagent was created successfully");
                        alert.showAndWait();
                    } else {
                        // Hubo un problema al insertar el reactivo
                        // Mostrar un mensaje de error al usuario
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Hubo un problema al crear el reactivo.|There was a problem creating the reagent.");
                        alert.showAndWait();
                    }
                } else {
                    // El nombre del grupo no existe en la tabla Claves
                    // Mostrar un mensaje de error al usuario
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("El nombre del grupo no es válido.|The group name is invalid.");
                    alert.showAndWait();
                }
                // Confirmar la transacción
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                // Manejar la excepción, probablemente hacer un rollback
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de errores
        }
    }

    @FXML
    private void comprobarIdBorrar(ActionEvent event) {
        // Obtener el ID ingresado por el usuario
        String idReactivo = idborarr.getText();

        // Realizar la consulta SQL para obtener la información del reactivo
        try (Connection connection = MySQLConnection.connectDB()) {
            String sql = "SELECT * FROM reactivos WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idReactivo);
            ResultSet resultSet = statement.executeQuery();

            // Verificar si se encontró algún resultado
            if (resultSet.next()) {
                // Mostrar la información del reactivo en el área de texto
                String informacion = "CAS: " + resultSet.getString("cas") + "\n" +
                        "Nombre: " + resultSet.getString("nombre") + "\n" +
                        "Volumen: " + resultSet.getString("volumen") + "\n" +
                        "Cantidad: " + resultSet.getString("cantidad") + "\n" +
                        "Proveedor: " + resultSet.getString("Proveedor") + "\n" +
                        "Grupo: " + resultSet.getString("grupo") + "\n" +
                        "Localización: " + resultSet.getString("localizacion") + "\n";

                vercomprovacion.setText(informacion);
            } else {
                // Si no se encontró ningún reactivo con ese ID, mostrar un mensaje al usuario
                // Por ejemplo, podrías usar una ventana emergente (Alert)
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No se encontró ningún reactivo con el ID proporcionado.|No reagent was found with the given ID.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de errores
        }
    }

    @FXML
    private void borrarReactivo(ActionEvent event) {
        // Verificar si el área de texto no está vacía
        if (!vercomprovacion.getText().isEmpty()) {
            // Obtener el ID ingresado por el usuario
            String idReactivo = idborarr.getText();

            // Realizar la eliminación del reactivo de la tabla
            try (Connection connection = MySQLConnection.connectDB()) {
                String sql = "DELETE FROM reactivos WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, idReactivo);

                // Ejecutar la sentencia SQL
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    connection.commit();
                    // El reactivo se eliminó correctamente
                    // Puedes mostrar un mensaje de éxito al usuario
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Éxito|Succes");
                    alert.setHeaderText(null);
                    alert.setContentText("El reactivo se ha eliminado correctamente.|The reagent has been disposed of successfully.");
                    alert.showAndWait();
                    vercomprovacion.clear();
                    idborarr.clear();
                } else {
                    // Hubo un problema al eliminar el reactivo
                    // Puedes mostrar un mensaje de error al usuario
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Hubo un problema al eliminar el reactivo.|There was a problem removing the reagent.");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Manejo de errores
            }
        } else {
            // El área de texto está vacía, mostrar un mensaje al usuario
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No hay ningún reactivo seleccionado para borrar.|There are no reagents selected to delete.");
            alert.showAndWait();
        }
    }
    @FXML
    private void Comprobar(ActionEvent event) {
        // Obtener el ID ingresado por el usuario
        String idReactivo = IDCREAR.getText();

        // Realizar la consulta SQL para obtener la información del reactivo
        try (Connection connection = MySQLConnection.connectDB()) {
            String sql = "SELECT * FROM reactivos WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idReactivo);
            ResultSet resultSet = statement.executeQuery();

            // Verificar si se encontró algún resultado
            if (resultSet.next()) {
                // Mostrar la información del reactivo
                cas.setText(resultSet.getString("cas"));
                volumen.setText(resultSet.getString("volumen"));
                nombre.setText(resultSet.getString("nombre"));
                Cantidad.setText(resultSet.getString("cantidad"));
                localizacion.setText(resultSet.getString("localizacion"));
                grupo.setText(resultSet.getString("grupo"));
                proveedor.setText(resultSet.getString("Proveedor"));
                // Verificar si es privado y común y seleccionar los CheckBox correspondientes
                boolean esPrivado = resultSet.getBoolean("privado");
                boolean esComun = resultSet.getBoolean("comunes");
                privado.setSelected(esPrivado);
                comunes.setSelected(esComun);
            } else {
                // Si no se encontró ningún reactivo con ese ID, mostrar un mensaje al usuario
                // Por ejemplo, podrías usar una ventana emergente (Alert)
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No se encontró ningún reactivo con el ID proporcionado.|No reagent was found with the given ID.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de errores
        }
    }

    @FXML
    private void actualizarReactivo(ActionEvent event) {
        // Obtener los valores editados por el usuario desde los campos de texto y checkboxes
        String casValue = cas.getText();
        if (casValue.matches("[0-9\\-]+")) {
            // La entrada contiene solo números y guiones
            // Puedes continuar con el procesamiento
        } else {
            // La entrada contiene caracteres no válidos
            // Mostrar un mensaje de alerta al usuario
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Entrada no válida");
            alert.setContentText("La entrada debe contener solo números y guiones.");
            alert.showAndWait();
            return;
        }
        String nombreValue = nombre.getText();
        String volumenValue = volumen.getText();
        String cantidadValue = Cantidad.getText();

        try {
            double cantidadNumerica = Double.parseDouble(cantidadValue);
            // Si la cantidad no es un número válido, lanzará NumberFormatException
            // En ese caso, se manejará en el bloque catch a continuación
        } catch (NumberFormatException e) {
            // La cantidad ingresada no es un número válido
            // Mostrar un mensaje de error al usuario
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("La cantidad ingresada no es un número válido.|\n" +
                    "The amount entered is not a valid number.");
            alert.showAndWait();
            return; // Salir del método ya que la cantidad no es válida
        }

        String proveedorValue = proveedor.getText();
        String grupoValue = grupo.getText();
        String localizacionValue = localizacion.getText();

        if (casValue.isEmpty() || nombreValue.isEmpty() || volumenValue.isEmpty() || cantidadValue.isEmpty() || proveedorValue.isEmpty() || localizacionValue.isEmpty()) {
            // Mostrar un mensaje de error al usuario
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete todos los campos obligatorios.|Please complete all required fields.");
            alert.showAndWait();
            return; // Salir del método ya que algún campo está vacío
        }

        //Verificar si es comun o privado
        boolean comunesValue = comunes.isSelected();
        boolean privadoValue = privado.isSelected();
        if(comunesValue == true){
            privadoValue = false;
        }
        if(privadoValue == true){
            comunesValue=false;
        }
        if (grupoValue.isEmpty()){
            comunesValue=true;
            privadoValue = false;
        }else{
            comunesValue=false;
        }


        // Obtener el ID del reactivo que se está actualizando
        String idReactivo = IDCREAR.getText(); // Esto asumo que es el ID del reactivo que se está mostrando/editando

        try (Connection connection = MySQLConnection.connectDB()) {
            // Consulta SQL para actualizar el reactivo en la base de datos
            String updateQuery = "UPDATE Reactivos SET cas = ?, nombre = ?, volumen = ?, cantidad = ?, Proveedor = ?, " +
                    "grupo = ?, localizacion = ?, comunes = ?, privado = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setString(1, casValue);
            statement.setString(2, nombreValue);
            statement.setString(3, volumenValue);
            statement.setString(4, cantidadValue);
            statement.setString(5, proveedorValue);
            statement.setString(6, grupoValue);
            statement.setString(7, localizacionValue);
            statement.setBoolean(8, comunesValue);
            statement.setBoolean(9, privadoValue);
            statement.setString(10, idReactivo);

            // Ejecutar la actualización
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                // La actualización se realizó correctamente
                // Mostrar un mensaje de éxito al usuario
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito|Succes");
                alert.setHeaderText(null);
                alert.setContentText("El reactivo se ha actualizado correctamente.|The reagent has been updated successfully.");
                alert.showAndWait();
                connection.commit();
            } else {
                // No se pudo actualizar el reactivo
                // Mostrar un mensaje de error al usuario
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un problema al actualizar el reactivo.|There was a problem updating the reagent.");
                alert.showAndWait();

            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar errores de SQL
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventariooiubo/Administracion/listadoadmin.fxml"));
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
}