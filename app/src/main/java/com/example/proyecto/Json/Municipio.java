
package com.example.proyecto.Json;

import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Municipio {


    public static final String CODIGO="codigo";
    public static final String MUNICIPIO="municipio";
    public static final String PROVINCIA="provincia";

    @SerializedName("Codigo")
    @Expose
    private String codigo;
    @SerializedName("Municipio")
    @Expose
    private String municipio;
    @SerializedName("Provincia")
    @Expose
    private String provincia;

    @Ignore
    public Municipio() {

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Municipio(String codigo, String municipio, String provincia) {
        this.codigo = codigo;
        this.municipio = municipio;
        this.provincia = provincia;
    }
}
