
package com.example.proyecto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Municipio {

    @SerializedName("Codigo")
    @Expose
    private String codigo;
    @SerializedName("Municipio")
    @Expose
    private String municipio;
    @SerializedName("Provincia")
    @Expose
    private String provincia;

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
