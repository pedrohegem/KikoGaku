
package com.example.proyecto.Json;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity
public class Montana {

    @SerializedName("Codigo")
    @Expose
    @PrimaryKey
    @NonNull
    @ColumnInfo
    private String codigo;

    @SerializedName("Nombre")
    @Expose
    @ColumnInfo
    private String nombre;

    public Montana(@NonNull String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
