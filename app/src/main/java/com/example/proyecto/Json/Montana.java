
package com.example.proyecto.Json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Montana {

    @SerializedName("Latitud")
    @Expose
    private Double latitud;
    @SerializedName("Longitud")
    @Expose
    private Double longitud;
    @SerializedName("Nombre")
    @Expose
    private String nombre;

    public Montana(Double latitud, Double longitud, String nombre) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}