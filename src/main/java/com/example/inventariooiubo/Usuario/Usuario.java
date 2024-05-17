package com.example.inventariooiubo.Usuario;

import com.example.inventariooiubo.MySQLConnection;
import com.example.inventariooiubo.TableRowData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario{

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
            String sql = "SELECT * FROM reactivos WHERE (privado = 0 OR comunes = 1) ";
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
            String sql = "SELECT * FROM reactivos WHERE privado = 0 ";
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
            String sql = "SELECT * FROM reactivos WHERE comunes = 1 ORDER BY comunes";
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
        String grupoBuscar = Grupo.getText();

        // Conexión a la base de datos
        try (Connection connection = MySQLConnection.connectDB()) {
            String sql;
            PreparedStatement statement = null;

            if (!nombreBuscar.isEmpty() && !casbuscar.isEmpty() && !grupoBuscar.isEmpty()) {
                sql = "SELECT * FROM reactivos WHERE ((privado = 0 OR comunes = 1)  AND (cas LIKE ? OR nombre LIKE ?) AND (grupo LIKE ? OR grupo IN (SELECT nombre FROM Login))";
                statement = connection.prepareStatement(sql);
                statement.setString(1, casbuscar.isEmpty() ? "%" : "%" + casbuscar + "%");
                statement.setString(2, nombreBuscar.isEmpty() ? "%" : "%" + nombreBuscar + "%");
                statement.setString(3, grupoBuscar.isEmpty() ? "%" : "%" + grupoBuscar + "%");
            } else if (!nombreBuscar.isEmpty() && !casbuscar.isEmpty()) {
                sql = "SELECT * FROM reactivos WHERE ((privado = 0 OR comunes = 1)  AND (cas LIKE ? OR nombre LIKE ?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1, "%" + casbuscar + "%");
                statement.setString(2, "%" + nombreBuscar + "%");
            } else if (!nombreBuscar.isEmpty()) {
                sql = "SELECT * FROM reactivos WHERE ((privado = 0 OR comunes = 1)  AND (nombre LIKE ?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1, "%" + nombreBuscar + "%");
            } else if (!casbuscar.isEmpty()) {
                sql = "SELECT * FROM reactivos WHERE ((privado = 0 OR comunes = 1) AND (cas LIKE ?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1, "%" + casbuscar + "%");
            } else if (!grupoBuscar.isEmpty()) {
                sql = "SELECT * FROM reactivos WHERE ((privado = 0 OR comunes = 1) AND (grupo LIKE ?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1, "%" + grupoBuscar + "%");
            }
            ResultSet resultSet = statement.executeQuery();

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
    public void EntrarNotas(ActionEvent event) {
        try {
            // Obtener el MenuItem actual
            MenuItem menuItem = (MenuItem) event.getSource();

            // Obtener la escena actual desde el MenuItem
            Scene scene = menuItem.getParentPopup().getScene();

            // Obtener el escenario actual desde el MenuItem
            Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

            // Cargar el FXML de AdmGrupo.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventariooiubo/Usuario/Notausuario.fxml"));
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
