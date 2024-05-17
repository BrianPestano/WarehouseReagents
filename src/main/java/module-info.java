module com.example.inventariooiubo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.graphics;

    opens com.example.inventariooiubo to javafx.fxml;
    exports com.example.inventariooiubo;
    exports com.example.inventariooiubo.Administracion; // Agregar esta línea
    opens com.example.inventariooiubo.Administracion to javafx.fxml;
    exports com.example.inventariooiubo.Grupo;
    opens com.example.inventariooiubo.Grupo to javafx.fxml;
    exports com.example.inventariooiubo.Usuario;
    opens com.example.inventariooiubo.Usuario to javafx.fxml;
}