package com.example.inventariooiubo.Administracion;

import com.example.inventariooiubo.MySQLConnection;
import com.example.inventariooiubo.TableRowData;
import com.sun.javafx.charts.Legend;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

import java.sql.*;
public class Listadoadmin {

    @FXML
    private String casbuscar ="";
    @FXML
    private String nombreBuscar ="";
    @FXML
    private TextField CAS;

    @FXML
    private TextField Nombre;

    @FXML
    private TextField Grupo;
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
    public void todos(ActionEvent event) {
        try (Connection connection = MySQLConnection.connectDB()) {
            String sql = "SELECT * FROM reactivos";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Crear una lista observable para los datos de la tabla
            ObservableList<TableRowData> data = FXCollections.observableArrayList();

            // Llenar la tabla con los datos de la consulta
            while (resultSet.next()) {
                // Obtener los datos de la fila actual
                String id = resultSet.getString("id");
                String cas = resultSet.getString("cas");
                String nombre = resultSet.getString("nombre");
                String volumen = resultSet.getString("volumen");
                String cantidad = resultSet.getString("cantidad");
                String proveedor = resultSet.getString("Proveedor");
                String grupo = resultSet.getString("grupo");
                String comunesValue = resultSet.getString("comunes");
                String comunes = "";
                if ("0".equals(comunesValue)) {
                    comunes = "No";
                } else if ("1".equals(comunesValue)) {
                    comunes = "SI";
                }
                String privadoValue = resultSet.getString("privado");
                String privado= "";
                if ("0".equals(privadoValue)) {
                    privado = "No";
                } else if ("1".equals(privadoValue)) {
                    privado = "SI";
                }
                String ubicacion = resultSet.getString("localizacion");
                String notas = resultSet.getString("notas");

                // Crear un objeto TableRowData para la fila actual y agregarlo a la lista
                TableRowData rowData = new TableRowData(id,cas, nombre, volumen, cantidad, proveedor, grupo, comunes, privado, ubicacion, notas);
                data.add(rowData);
            }

            // Establecer el conjunto completo de datos en la tabla
            Tablareactivos.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void grupo(ActionEvent event) {
        try (Connection connection = MySQLConnection.connectDB()) {
            String sql = "SELECT * FROM reactivos WHERE grupo IS NOT NULL AND grupo != '' ORDER BY grupo";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Crear una lista observable para los datos de la tabla
            ObservableList<TableRowData> data = FXCollections.observableArrayList();

            // Llenar la tabla con los datos de la consulta
            while (resultSet.next()) {
                // Obtener los datos de la fila actual
                String id = resultSet.getString("id");
                String cas = resultSet.getString("cas");
                String nombre = resultSet.getString("nombre");
                String volumen = resultSet.getString("volumen");
                String cantidad = resultSet.getString("cantidad");
                String proveedor = resultSet.getString("Proveedor");
                String grupo = resultSet.getString("grupo");
                String comunesValue = resultSet.getString("comunes");
                String comunes = "";
                if ("0".equals(comunesValue)) {
                    comunes = "No";
                } else if ("1".equals(comunesValue)) {
                    comunes = "SI";
                }
                String privadoValue = resultSet.getString("privado");
                String privado = "";
                if ("0".equals(privadoValue)) {
                    privado = "No";
                } else if ("1".equals(privadoValue)) {
                    privado = "SI";
                }
                String ubicacion = resultSet.getString("localizacion");
                String notas = resultSet.getString("notas");

                // Crear un objeto TableRowData para la fila actual y agregarlo a la lista
                TableRowData rowData = new TableRowData(id, cas, nombre, volumen, cantidad, proveedor, grupo, comunes, privado, ubicacion, notas);
                data.add(rowData);
            }

            // Establecer el conjunto completo de datos en la tabla
            Tablareactivos.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void comunes(ActionEvent event) {
        try (Connection connection = MySQLConnection.connectDB()) {
            String sql = "SELECT * FROM reactivos WHERE comunes = true OR comunes = 1 ORDER BY comunes";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Crear una lista observable para los datos de la tabla
            ObservableList<TableRowData> data = FXCollections.observableArrayList();

            // Llenar la tabla con los datos de la consulta
            while (resultSet.next()) {
                // Obtener los datos de la fila actual
                String id = resultSet.getString("id");
                String cas = resultSet.getString("cas");
                String nombre = resultSet.getString("nombre");
                String volumen = resultSet.getString("volumen");
                String cantidad = resultSet.getString("cantidad");
                String proveedor = resultSet.getString("Proveedor");
                String grupo = resultSet.getString("grupo");
                String comunesValue = resultSet.getString("comunes");
                String comunes= "";
                if ("0".equals(comunesValue)) {
                    comunes = "No";
                } else if ("1".equals(comunesValue)) {
                    comunes = "SI";
                }
                String privadoValue = resultSet.getString("privado");
                String privado= "";
                if ("0".equals(privadoValue)) {
                    privado = "No";
                } else if ("1".equals(privadoValue)) {
                    privado = "SI";
                }
                String ubicacion = resultSet.getString("localizacion");
                String notas = resultSet.getString("notas");

                // Crear un objeto TableRowData para la fila actual y agregarlo a la lista
                TableRowData rowData = new TableRowData(id,cas, nombre, volumen, cantidad, proveedor, grupo, comunes, privado, ubicacion, notas);
                data.add(rowData);
            }

            // Establecer el conjunto completo de datos en la tabla
            Tablareactivos.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void buscar(KeyEvent event) {
        String casbuscar = CAS.getText();
        String nombreBuscar = Nombre.getText();
        String grupobuscar =  Grupo.getText();

        // Conexión a la base de datos
        try (Connection connection = MySQLConnection.connectDB()) {
            String sql;
            PreparedStatement statement = null;

            ResultSet resultSet = null;
            if (!nombreBuscar.isEmpty() && !casbuscar.isEmpty() && !grupobuscar.isEmpty()) {
                sql = "SELECT * FROM reactivos WHERE cas LIKE ? AND nombre LIKE ? AND grupo LIKE ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, nombreBuscar.isEmpty() ? "%" : "%" + nombreBuscar + "%");
                statement.setString(2, casbuscar.isEmpty() ? "%" : "%" + casbuscar + "%");
                statement.setString(3, grupobuscar.isEmpty() ? "%" : "%" + grupobuscar + "%");
            } else if (!nombreBuscar.isEmpty()) {
                // Modificar la consulta para buscar nombres que comiencen con la letra ingresada
                sql = "SELECT * FROM reactivos WHERE nombre LIKE ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, "%" + nombreBuscar + "%");
            } else if (!casbuscar.isEmpty()) {
                // Modificar la consulta para buscar nombres que comiencen con la letra ingresada
                sql = "SELECT * FROM reactivos WHERE cas LIKE ? ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, "%" + casbuscar + "%");
            } else if (!grupobuscar.isEmpty()) {
                // Modificar la consulta para buscar nombres que comiencen con la letra ingresada
                sql = "SELECT * FROM reactivos WHERE grupo LIKE ? ";
                statement = connection.prepareStatement(sql);
                statement.setString(1, "%" + grupobuscar + "%");
            }

            // Ejecutar la consulta y obtener el resultado
            resultSet = statement.executeQuery();

            // Crear una lista observable para los datos de la tabla
            ObservableList<TableRowData> data = FXCollections.observableArrayList();

            // Llenar la tabla con los datos de la consulta
            while (resultSet.next()) {
                // Obtener los datos de la fila actual
                String id = resultSet.getString("id");
                String cas = resultSet.getString("cas");
                String nombre = resultSet.getString("nombre");
                String volumen = resultSet.getString("volumen");
                String cantidad = resultSet.getString("cantidad");
                String proveedor = resultSet.getString("Proveedor");
                String grupo = resultSet.getString("grupo");
                String comunesValue = resultSet.getString("comunes");
                String comunes = ("0".equals(comunesValue)) ? "No" : "Sí";
                String privadoValue = resultSet.getString("privado");
                String privado = ("0".equals(privadoValue)) ? "No" : "Sí";
                String ubicacion = resultSet.getString("localizacion");
                String notas = resultSet.getString("notas");

                // Crear un objeto TableRowData para la fila actual y agregarlo a la lista
                TableRowData rowData = new TableRowData(id, cas, nombre, volumen, cantidad, proveedor, grupo, comunes, privado, ubicacion, notas);
                data.add(rowData);
            }

            // Establecer el conjunto completo de datos en la tabla
            Tablareactivos.setItems(data);

        } catch (SQLException e) {
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