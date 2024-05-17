package com.example.inventariooiubo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableRowData {
    private final StringProperty id;
    private final StringProperty cas;
    private final StringProperty nombre;
    private final StringProperty volumen;
    private final StringProperty cantidad;
    private final StringProperty proveedor;
    private final StringProperty grupo;
    private final StringProperty comunes;
    private final StringProperty privado;
    private final StringProperty ubicacion;
    private final StringProperty notas;

    public TableRowData(String id ,String cas, String nombre, String volumen, String cantidad, String proveedor, String grupo, String comunes, String privado, String ubicacion, String notas) {
        this.id = new SimpleStringProperty(id);
        this.cas = new SimpleStringProperty(cas);
        this.nombre = new SimpleStringProperty(nombre);
        this.volumen = new SimpleStringProperty(volumen);
        this.cantidad = new SimpleStringProperty(cantidad);
        this.proveedor = new SimpleStringProperty(proveedor);
        this.grupo = new SimpleStringProperty(grupo);
        this.comunes = new SimpleStringProperty(comunes);
        this.privado = new SimpleStringProperty(privado);
        this.ubicacion = new SimpleStringProperty(ubicacion);
        this.notas = new SimpleStringProperty(notas);
    }

    // Getters con convención de nomenclatura de JavaBeans
    public String getCas() {
        return cas.get();
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty casProperty() {
        return cas;
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getVolumen() {
        return volumen.get();
    }

    public StringProperty volumenProperty() {
        return volumen;
    }

    public String getCantidad() {
        return cantidad.get();
    }

    public StringProperty cantidadProperty() {
        return cantidad;
    }

    public String getProveedor() {
        return proveedor.get();
    }

    public StringProperty proveedorProperty() {
        return proveedor;
    }

    public String getGrupo() {
        return grupo.get();
    }

    public StringProperty grupoProperty() {
        return grupo;
    }

    public String getComunes() {
        return comunes.get();
    }

    public StringProperty comunesProperty() {
        return comunes;
    }

    public String getPrivado() {
        return privado.get();
    }

    public StringProperty privadoProperty() {
        return privado;
    }

    public String getUbicacion() {
        return ubicacion.get();
    }

    public StringProperty ubicacionProperty() {
        return ubicacion;
    }

    public String getNotas() {
        return notas.get();
    }

    public StringProperty notasProperty() {
        return notas;
    }
}