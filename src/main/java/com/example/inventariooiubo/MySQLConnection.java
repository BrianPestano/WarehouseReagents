package com.example.inventariooiubo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {

    private static final String USER_DB = "root";
    private static final String PASSWORD_DB = ""; // La contraseña debería estar protegida
    private static final String URL = "jdbc:mysql://localhost:3306/inventario_iubo"; // URL completa con el nombre de la base de datos

    public static Connection connectDB() {
        Connection mySQLConnection = null;
        try {
            mySQLConnection = DriverManager.getConnection(URL, USER_DB, PASSWORD_DB);
            mySQLConnection.setAutoCommit(false);
            System.out.println("[i] Conexión exitosa.");
        } catch (SQLException e) {
            System.out.println("[!] No se ha podido establecer conexión con la base de datos");
            e.printStackTrace();
        }
        return mySQLConnection;
    }

    public void crearTablas(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            // Crear tabla Claves si no existe
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Claves ("
                    + "nombre VARCHAR(255) PRIMARY KEY,"
                    + "contraseña VARCHAR(255)"
                    + ")");
            // Crear tabla Login si no existe
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Login ("
                    + "nombre VARCHAR(255) PRIMARY KEY"
                    + ")");
            // Crear tabla Registro si no existe
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Reactivos ("
                    + "id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,"
                    + "cas VARCHAR(255), "
                    + "nombre VARCHAR(255), "
                    + "volumen VARCHAR(255), "
                    + "cantidad VARCHAR(255), "
                    + "Proveedor VARCHAR(255), "
                    + "grupo VARCHAR(255) DEFAULT NULL, "
                    + "localizacion VARCHAR(255), "
                    + "notas VARCHAR(255) DEFAULT NULL, "
                    + "comunes BOOLEAN, "
                    + "privado BOOLEAN"
                    + ")");

            System.out.println("Tablas creadas exitosamente.");
        } catch (SQLException ex) {
            System.err.println("Error al crear las tablas.");
            ex.printStackTrace();
        }
    }

    public boolean tablasExisten(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            // Verificar si existen las tablas Administracion, Historial y DatosPersonales
            boolean ReactivosTableExists = statement.executeQuery("SHOW TABLES LIKE 'Reactivos'").next();
            boolean NotasTableExists = statement.executeQuery("SHOW TABLES LIKE 'Notas'").next();
            boolean loginDataTableExists = statement.executeQuery("SHOW TABLES LIKE 'Login'").next();
            boolean clavesDataTableExists = statement.executeQuery("SHOW TABLES LIKE 'Claves'").next();
            return ReactivosTableExists && NotasTableExists && loginDataTableExists&&clavesDataTableExists;
        } catch (SQLException ex) {
            System.err.println("Error al verificar la existencia de las tablas.");
            ex.printStackTrace();
            return false;
        }
    }
}